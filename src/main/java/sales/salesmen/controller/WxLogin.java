package sales.salesmen.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

@Controller
public class WxLogin {

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
            try {
                response.sendRedirect(getUserInfoUrl(request));
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            if (wxUser.getUser() == null) {
                try {
                    response.sendRedirect(getUserInfoUrl(request));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                 //判断是不是有手机号和权限
                if(wxUser.getUser().getPhonenum()==null|| wxUser.getUser().getAuthorities()==null){
                    Long userid = wxUser.getUser().getId();

                    PasswordEncoder encoder = new BCryptPasswordEncoder();
                    String encodeUserid = encoder.encode(String.valueOf(userid));

                    redisService.set("encodeUserid_" + encodeUserid, String.valueOf(userid));
                    return "redirect:/setauthorityandphonenum?uid=" + encodeUserid;

                }else {
                    try {
                        request.getSession().setAttribute("wxlogin",true);

                        String InterceptedUrl= (String) request.getSession().getAttribute("Interceptedurl");
                        request.getSession().removeAttribute("Interceptedurl");

                        response.sendRedirect(InterceptedUrl);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

//        session.setAttribute("wxlogin", true);

        return "/";
    }


    @GetMapping("/setauthorityandphonenum")
    public String setauthorityandphonenum(@RequestParam("uid") String encodeUserid, Model model) {

//        long userid = Long.valueOf(redisService.get("encodeUserid_" + encodeUserid));
//
//        User user=userRepository.findById(userid).get();

        String errMsgs="验证码错误";
        model.addAttribute("errmsg",errMsgs);


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
                                              Model model,HttpServletRequest request, HttpServletResponse response) {
        //如果验证码正确
        if (true) {


            long userid = Long.valueOf(redisService.get("encodeUserid_" + encodeUserid));
            redisService.remove("encodeUserid_" + encodeUserid);

            User user = userRepository.findById(userid).get();
            user.setPhonenum(phonenum);
            List<Authority> authorities=new ArrayList<>();
            authorities.add(authorityService.getAuthorityById(Long.valueOf(authorityid)).get());

            userRepository.save(user);

            String InterceptedUrl= (String) request.getSession().getAttribute("Interceptedurl");
            request.getSession().removeAttribute("Interceptedurl");

            try {
                request.getSession().setAttribute("wxlogin",true);

                response.sendRedirect(InterceptedUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }
        else {
            String errMsgs="验证码错误";
            model.addAttribute("errmsg",errMsgs);
            return "/setauthorityandphonenum";
        }
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

        Long userid = wxUser.getUser().getId();

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodeUserid = encoder.encode(String.valueOf(userid));

        redisService.set("encodeUserid_" + encodeUserid, String.valueOf(userid));

        return "redirect:/setauthorityandphonenum?uid=" + encodeUserid;
    }


    //获取用户信息的url
    private String getUserInfoUrl(HttpServletRequest request) {

        String encoded_wechat_redirect_uri = "";
        try {
            encoded_wechat_redirect_uri = URLEncoder.encode(UrlUtil.getBaseUrl(request) + this.userinfo_redirect_uri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=2#wechat_redirect", this.appid, encoded_wechat_redirect_uri);
    }

}
