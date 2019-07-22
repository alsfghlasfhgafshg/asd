package sales.salesmen.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sales.salesmen.entity.Authority;
import sales.salesmen.entity.User;
import sales.salesmen.entity.WxUser;
import sales.salesmen.model.WXUserinfo;
import sales.salesmen.repository.AuthorityRepository;
import sales.salesmen.repository.UserRepository;
import sales.salesmen.repository.WxUserRepository;
import sales.salesmen.service.AuthorityService;
import sales.salesmen.service.RedisService;
import sales.salesmen.service.WxService;
import sales.salesmen.utils.UrlUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class WxLogin {

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    RedisService redisService;


    @Autowired
    private AuthorityService authorityService;

    @Value("${appid}")
    String appid;

    String userinfo_redirect_uri;

    @Autowired
    WxService wxService;

    @Autowired
    WxUserRepository wxUserRepository;

    @Autowired
    UserRepository userRepository;

    public WxLogin(@Value("${wechat_userinfo_redirect_uri}") String userinfo_redirect_uri) {
        if (userinfo_redirect_uri.startsWith("/")) {
            this.userinfo_redirect_uri = userinfo_redirect_uri;
        } else {
            this.userinfo_redirect_uri = "/" + userinfo_redirect_uri;
        }
    }

    //获取openid
    @GetMapping("/wxlogin")
    @Transactional
    public @ResponseBody
    String wxLogin(HttpSession session, HttpServletRequest request, HttpServletResponse response, @RequestParam("code") String code) {

        JSONObject jsonObject = wxService.code2OpenidAndAccessToken(code);
        String openid = jsonObject.getString("openid");
        WxUser wxUser = wxUserRepository.findWxUserByWxopenid(openid);

        if (wxUser == null) {
            wxUser = new WxUser();
            wxUser.setWxopenid(openid);
            wxUserRepository.save(wxUser);
        } else {
            if (wxUser.getUser() != null && wxUser.getUser().getAuthorities().size() != 0) {
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                        wxUser.getUser(), null, wxUser.getUser().getAuthorities()));
                session.setAttribute("iswxlogin", true);
            }
        }
        session.setAttribute("hasopenid", true);
        try {
            String InterceptedUrl = (String) request.getSession().getAttribute("Interceptedurl");
            InterceptedUrl = redirectIndexOrUrl(InterceptedUrl);
            response.sendRedirect(InterceptedUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //redirect to Interceptedurl
        return "redirect to Interceptedurl";
    }


    @GetMapping("/setauthorityandphonenum")
    public String setauthorityandphonenum(@RequestParam("uid") String encodeUserid, Model model, @RequestParam(value = "errmsg", required = false) String errmsg) {

        if (errmsg != null) {
            model.addAttribute("errmsg", errmsg);
        }

        List<Authority> authorities = authorityService.findAll();
        for (Authority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                authorities.remove(authority);
                break;
            }
        }
        model.addAttribute("uid", encodeUserid);
        model.addAttribute("as", authorities);

        return "setauthorityandphonenum";
    }


    @PostMapping("/setauthorityandphonenum")
    public String postsetauthorityandphonenum(@RequestParam("uid") String encodeUserid, @RequestParam("authorityid") int authorityid,
                                              @RequestParam("phonenum") String phonenum, @RequestParam("verificationcode") int verificationcode,
                                              Model model, HttpServletRequest request, HttpServletResponse response) {
        //如果验证码正确
        if (true) {


            long userid = Long.valueOf(redisService.get("encodeUserid_" + encodeUserid));
            redisService.remove("encodeUserid_" + encodeUserid);

            User user = userRepository.findById(userid).get();
            user.setPhonenum(phonenum);
            List<Authority> authorities = new ArrayList<>();
            authorities.add(authorityService.getAuthorityById(Long.valueOf(authorityid)).get());
            user.setAuthorities(authorities);

            userRepository.save(user);

            Authority userauthority = authorityRepository.findById(Long.valueOf(authorityid)).get();
            ArrayList<Authority> authorityArrayList = new ArrayList<>();
            authorityArrayList.add(userauthority);

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                    userRepository.findById(userid).get(), null, authorityArrayList));

            String InterceptedUrl = (String) request.getSession().getAttribute("Interceptedurl");
            InterceptedUrl = redirectIndexOrUrl(InterceptedUrl);

            request.getSession().removeAttribute("Interceptedurl");
            request.getSession().setAttribute("iswxlogin", true);

            try {
                response.sendRedirect(InterceptedUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        } else {
            return "redirect:/setauthorityandphonenum?errmsg=验证码错误";
        }
    }

    private String redirectIndexOrUrl(String InterceptedUrl) {
        if (InterceptedUrl.startsWith("/myself") || InterceptedUrl.startsWith("/home")
                || InterceptedUrl.startsWith("/productservice") || InterceptedUrl.startsWith("/course")) {
            return "/";
        } else return InterceptedUrl;

    }


    //获取用户信息
    @GetMapping("/wxuserinfo")
    @Transactional
    public String wxUserInfo(HttpSession session, HttpServletRequest request, @RequestParam("code") String code) {
        JSONObject jsonObject = wxService.code2OpenidAndAccessToken(code);

        String openid = jsonObject.getString("openid");
        String accesstoken = jsonObject.getString("access_token");

        WXUserinfo wxUserinfo = wxService.accesstoken2Userinfo(accesstoken, openid);

        WxUser wxUser = wxUserRepository.findWxUserByWxopenid(openid);

        if (wxUser == null) {
            wxUser = new WxUser();
            wxUser.setWxopenid(openid);
            wxUserRepository.save(wxUser);
        }

        User u = wxUser.getUser();
        if (u == null) {
            u = new User();

            u.setCreatetime(new Timestamp(System.currentTimeMillis()));
            u.setDisabled(false);
            u.setAvatar(wxUserinfo.getHeadimgurl());
            u.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
            u.setUsername(wxUserinfo.getNickname());

            wxUser.setUser(u);
            wxUserRepository.save(wxUser);
        }

        Long userid = wxUserRepository.findWxUserByWxopenid(openid).getUser().getId();

        String uuidUserid = UUID.randomUUID().toString();
        redisService.set("encodeUserid_" + uuidUserid, String.valueOf(userid));
        return "redirect:/setauthorityandphonenum?uid=" + uuidUserid;
    }

}
