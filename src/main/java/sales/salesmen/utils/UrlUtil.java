package sales.salesmen.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class UrlUtil {

    public static String getBaseUrl(HttpServletRequest request) {
        StringBuilder s = new StringBuilder();
        s.append(request.getScheme());
        s.append("://");
        s.append(request.getRemoteHost());

        if (!request.getScheme().equals("https")) {

            s.append(":");
            s.append(request.getLocalPort());
        }
        return s.toString();
    }

    public static String getUrlWithParameterNames(HttpServletRequest request){
        Enumeration<String> parameterNames = request.getParameterNames();
        StringBuilder fullurl=new StringBuilder();
        fullurl.append(request.getRequestURL());
        boolean wenhao=true;

        while (parameterNames.hasMoreElements()){
            if(wenhao==true){
                fullurl.append("?");
                wenhao=false;
            }else {
                fullurl.append("&");
            }
            String tempParameterName=parameterNames.nextElement();
            fullurl.append(tempParameterName);
            fullurl.append("=");
            fullurl.append(request.getParameter(tempParameterName));

        }

        return fullurl.toString();



    }
}
