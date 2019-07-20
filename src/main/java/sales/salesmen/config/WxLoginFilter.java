package sales.salesmen.config;

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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Pattern;

@Component
public class WxLoginFilter extends OncePerRequestFilter {

    String appid;
    String appsecret;
    String redirect_uri;

    String[] needloginpatterns = new String[]{
            "/",
            "/article*",
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
    public WxLoginFilter(@Value("${wechat_redirect_uri}") String redirect_uri, @Value("${appid}") String appid, @Value("${appsecret}") String appsecret) {
        this.appid = appid;
        this.appsecret = appsecret;
        if (redirect_uri.startsWith("/")) {
            this.redirect_uri = redirect_uri;
        } else {
            this.redirect_uri = "/" + redirect_uri;
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean needlogin = false;

        String uri = request.getRequestURI();
        String url = UrlUtil.getUrlWithParameterNames(request);


        //是不是直接跳过
        if (isMatchPatterns(uri, passpatterns)) {
            filterChain.doFilter(request, response);
            return;
        }

//        if (uri.equals("/wxlogin")) {
//            filterChain.doFilter(request, response);
//            return;
//        }


        //判断是不是需要微信授权
        needlogin = isMatchPatterns(uri, needloginpatterns);

//        for (String uripattern : needloginpatterns) {
//            if (Pattern.matches(uripattern, uri)) {
//                needlogin = true;
//                break;
//            }
//        }

        //获取ua
        String ua = request.getHeader("User-Agent");

        if (ua.contains("MicroMessenger")) {
            if (needlogin) {
                //是微信浏览器并且需要登录的url

                HttpSession session = request.getSession();

                //设置要登录成功之后跳转的页面

//                session.setAttribute();

                Boolean wxlogin = (Boolean) session.getAttribute("wxlogin");

                if (wxlogin == null || wxlogin == false) {
                    //没登录，调转到微信授权页

                    //保存被拦截的url
                    if (request.getMethod().equals("GET")) {
                        if (session.getAttribute("Interceptedurl") == null) {
                            session.setAttribute("Interceptedurl", UrlUtil.getUrlWithParameterNames(request));
                        }
                    }

                    String redirectUrl = getWxAuthUrl(request);
                    response.sendRedirect(redirectUrl);
                } else {
                    //已登录，跳过
                    filterChain.doFilter(request, response);
                    return;
                }

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                int a = 1;

            }
        } else {
            //不是微信浏览器，直接跳过
            filterChain.doFilter(request, response);
            return;
        }


    }
}
