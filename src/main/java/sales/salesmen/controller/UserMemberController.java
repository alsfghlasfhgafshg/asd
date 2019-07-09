package sales.salesmen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sales.salesmen.service.RedisService;
import sales.salesmen.service.UserMemberService;

@Controller
@RequestMapping("/auth")
public class UserMemberController {

    @Autowired
    private UserMemberService userMemberService;

    @GetMapping("/getAuthCode")
    public String getAuthCode(@RequestParam("phonenum")String phonenum, Model model){
        String Authcode = userMemberService.generateAuthCode(phonenum);
        System.out.println(Authcode);
        model.addAttribute("authcode",Authcode);
        return "yanzheng";
    }

    @PostMapping("/verifyAuthcode")
    @ResponseBody
    public boolean verify(@RequestParam("phonenum")String phonenum,
                          @RequestParam("authcode")String authcode){
        boolean pass = false;
        pass = userMemberService.verifyAuthCode(phonenum,authcode);
        System.out.println(pass);
        return pass;
    }

}
