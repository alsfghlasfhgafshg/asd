package sales.salesmen.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.regex.Pattern;

@Component
public class WxLoginFilter extends OncePerRequestFilter {


    String appid;
    String appsecret;

    String wxauthurl;


    String[] uripatterns = new String[]{
            "/",
            "/article*",
    };


    @Autowired
    public WxLoginFilter(@Value("${wechat_redirect_uri}") String redirect_uri, @Value("${appid}") String appid, @Value("${appsecret}") String appsecret) {
        this.appid = appid;
        this.appsecret = appsecret;

        String encoded_wechat_redirect_uri = "";
        try {
            encoded_wechat_redirect_uri = URLEncoder.encode(redirect_uri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.wxauthurl = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=1#wechat_redirect", this.appid, encoded_wechat_redirect_uri);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean needlogin = false;

        String uri = request.getRequestURI();
        String url = request.getRequestURL().toString();

        //是wxlogin 直接跳过
        if(uri.equals("/wxlogin")){
            filterChain.doFilter(request, response);
            return;
        }

        //判断是不是需要微信授权
        for (String uripattern : uripatterns) {
            if (Pattern.matches(uripattern, uri)) {
                needlogin = true;
                break;
            }
        }

        //获取ua
        String ua = request.getHeader("User-Agent");

        if (ua.contains("MicroMessenger")) {
            //是微信浏览器
            HttpSession session = request.getSession();

            if ((Boolean) session.getAttribute("wxlogin") == null ||(Boolean) session.getAttribute("wxlogin") == false) {
                //没登录，调转到微信授权页
                response.sendRedirect(wxauthurl);
            } else {
                //已登录，跳过
                filterChain.doFilter(request, response);
                return;
            }

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            int a = 1;
        } else {
            //不是微信浏览器，直接跳过
            filterChain.doFilter(request, response);
            return;
        }


    }
}
