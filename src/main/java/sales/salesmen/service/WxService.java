package sales.salesmen.service;


import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Component
public class WxService {


    String appid;
    String appsecret;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    public WxService(@Value("${appid}") String appid, @Value("${appsecret}") String appsecret) {
        this.appid = appid;
        this.appsecret = appsecret;
    }


    public JSONObject code2Openid(String code) {
        String url = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code", this.appid, this.appsecret, code);

        JSONObject jsonObject = restTemplate.getForObject(url,JSONObject.class);
        return jsonObject;
    }
}
