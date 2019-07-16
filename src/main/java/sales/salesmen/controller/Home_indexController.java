package sales.salesmen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import sales.salesmen.entity.Article;
import sales.salesmen.service.ArticleService;

@Controller
@RequestMapping("/home")
public class Home_indexController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/article/{id}")
    public String viewArticle(@PathVariable("id")Long id, Model model){

        Article article = articleService.getArticleById(id).get();
        model.addAttribute("articleModel",article);
        return "/home/home_article";
    }
}
