package sales.salesmen.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String myself(){
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
