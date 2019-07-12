package sales.salesmen.service;


import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import sales.salesmen.model.WXUserinfo;

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


    public JSONObject code2OpenidAndAccessToken(String code) {
        String url = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code", this.appid, this.appsecret, code);

        String response = restTemplate.getForObject(url,String.class);

        JSONObject jsonObject = JSONObject.parseObject(response);
        return jsonObject;
    }

    public WXUserinfo accesstoken2Userinfo(String accesstoken,String openid){
        String url=String.format("https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN",accesstoken,openid);
        WXUserinfo wxUserinfo = restTemplate.getForObject(url, WXUserinfo.class);

        return wxUserinfo;
    }
}
