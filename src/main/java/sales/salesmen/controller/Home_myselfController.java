package sales.salesmen.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sales.salesmen.entity.User;
import sales.salesmen.service.ArticleLookedUserService;

import java.util.List;

@Controller
public class Home_myselfController {


    @Autowired
    ArticleLookedUserService articleLookedUserService;

    @GetMapping("/myself/myclient")
    public String myclient(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken,
                           Model model) {

        Long uid = ((User) usernamePasswordAuthenticationToken.getPrincipal()).getId();

        List<User> allmyclient = articleLookedUserService.allmyclient(uid);
        model.addAttribute("allmyclient",allmyclient);

        return "home/myself/myclient";
    }


}
