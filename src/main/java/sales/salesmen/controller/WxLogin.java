package sales.salesmen.controller;

import com.alibaba.fastjson.JSONObject;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import sales.salesmen.entity.WxUser;
import sales.salesmen.repository.WxUserRepository;
import sales.salesmen.service.WxService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
public class WxLogin {

    @Autowired
    WxService wxService;

    @Autowired
    WxUserRepository wxUserRepository;


    @GetMapping("/wxlogin")
    public String wxlogin(HttpSession session, HttpServletRequest request, @RequestParam("code") String code){
//        session.setAttribute("wxlogin",true);
        JSONObject jsonObject = wxService.code2Openid(code);
        String openid=jsonObject.getString("openid");

        Optional<WxUser> wxUser=wxUserRepository.findById(code);
        int a=1;


        return openid;
    }
}
