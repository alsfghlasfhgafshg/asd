package sales.salesmen.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sales.salesmen.entity.Article;
import sales.salesmen.entity.User;
import sales.salesmen.service.ArticleService;
import sales.salesmen.utils.TimeCompareUtil;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/home")
    public ModelAndView home(@RequestParam(value = "pageIndex",required = false,defaultValue = "0")int pageIndex,
                             @RequestParam(value = "pageSize",required = false,defaultValue = "10")int pageSize,
                             Model model){
        Pageable pageable = PageRequest.of(pageIndex,pageSize);
        Page<Article> page = articleService.listAllArticle(pageable);
        List<Article> list = page.getContent();
        Date date = new Date();
        TimeCompareUtil timeCompareUtil = new TimeCompareUtil();
        for (Article article:list){
            article.setTimeDifference(timeCompareUtil.timeDifference(date,article.getCreateTime()));
        }
        model.addAttribute("articleList",list);
        return new ModelAndView("page/home","articleModel",model);
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
