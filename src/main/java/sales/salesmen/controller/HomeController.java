package sales.salesmen.controller;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sales.salesmen.entity.User;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/home")
    public String home(){
        return "page/home";
    }

    @GetMapping("/myself")
    public String myself(Principal principal,Model model){
        String avatarimg=null;
        if(principal!=null){
            avatarimg=((User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getAvatar();
        }

        if(avatarimg==null){
            avatarimg="/img/star.png";
        }

        if(principal==null){
            model.addAttribute("avatarimg","/img/star.png");
        }else {
            model.addAttribute("avatarimg",avatarimg);
        }

        return "page/myself";
    }

    @GetMapping("/productservice")
    public String productservice(){
        return "page/productservice";
    }

    @GetMapping("/course")
    public String course(){
        return "page/course";
    }


}
