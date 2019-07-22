package sales.salesmen.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sales.salesmen.utils.UrlUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Pattern;

@Component
public class WxLoginFilter extends OncePerRequestFilter {

    String appid;
    String appsecret;
    String redirect_uri;

    String userinfo_redirect_uri;

    String[] needloginpatterns = new String[]{
            "/myself*"
//            "/article*",
    };

    String[] passpatterns = new String[]{
            "/wxuserinfo*",
            "/wxlogin*",
            "/setauthorityandphonenum*"
    };


    private boolean isMatchPatterns(String uri, String[] uripatterns) {
        for (String uripattern : uripatterns) {
            if (Pattern.matches(uripattern, uri)) {
                return true;
            }
        }
        return false;

    }

    private String getWxAuthUrl(HttpServletRequest request) {

        String encoded_wechat_redirect_uri = "";
        try {
            encoded_wechat_redirect_uri = URLEncoder.encode(UrlUtil.getBaseUrl(request) + redirect_uri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=1#wechat_redirect", this.appid, encoded_wechat_redirect_uri);
    }

    @Autowired
    public WxLoginFilter(@Value("${wechat_userinfo_redirect_uri}") String userinfo_redirect_uri, @Value("${wechat_redirect_uri}") String redirect_uri, @Value("${appid}") String appid, @Value("${appsecret}") String appsecret) {
        this.appid = appid;
        this.appsecret = appsecret;
        if (redirect_uri.startsWith("/")) {
            this.redirect_uri = redirect_uri;
        } else {
            this.redirect_uri = "/" + redirect_uri;
        }

        if (userinfo_redirect_uri.startsWith("/")) {
            this.userinfo_redirect_uri = userinfo_redirect_uri;
        } else {
            this.userinfo_redirect_uri = "/" + userinfo_redirect_uri;
        }
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


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String ua = request.getHeader("User-Agent");
        //判断是不是微信的浏览器和method是不是get
        if (!ua.contains("MicroMessenger") || !request.getMethod().equals("GET")) {
            filterChain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession();

        String uri = request.getRequestURI();
        String url = UrlUtil.getUrlWithParameterNames(request);


        Boolean hasopenid = (Boolean) session.getAttribute("hasopenid");

        if (isMatchPatterns(uri, passpatterns)) {
            filterChain.doFilter(request, response);
            return;
        }


        if (hasopenid == null || hasopenid == false) {
            session.setAttribute("Interceptedurl", url);
            String redirectUrl = getWxAuthUrl(request);
            response.sendRedirect(redirectUrl);
            return;
        }

        //保证有openid
        if (isMatchPatterns(uri, needloginpatterns)) {
            session.setAttribute("Interceptedurl", UrlUtil.getUrlWithParameterNames(request));
            Boolean iswxlogin = (Boolean) session.getAttribute("iswxlogin");
            if (iswxlogin != null && iswxlogin == true) {
                filterChain.doFilter(request, response);
                return;
            } else {
                response.setStatus(401);
                PrintWriter writer = response.getWriter();
                JSONObject responsejson = new JSONObject();
                responsejson.put("url", getUserInfoUrl(request));
                writer.write(JSONObject.toJSONString(responsejson));
                writer.close();
                return;
            }
        }
        filterChain.doFilter(request, response);
        return;
    }
}
