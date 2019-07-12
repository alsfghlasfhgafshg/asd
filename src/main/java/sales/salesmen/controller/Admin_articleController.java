package sales.salesmen.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sales.salesmen.entity.ACatalog;
import sales.salesmen.entity.Article;
import sales.salesmen.entity.Authority;
import sales.salesmen.entity.User;
import sales.salesmen.service.ACatalogService;
import sales.salesmen.service.ArticleService;
import sales.salesmen.vo.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/articles")
public class Admin_articleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ACatalogService aCatalogService;

    @GetMapping
    public ModelAndView getArticle(@RequestParam(value = "async",required = false)boolean async,
                                   @RequestParam(value = "pageIndex",required = false,defaultValue = "0")int pageIndex,
                                   @RequestParam(value = "pageSize",required = false,defaultValue = "10")int pageSize,
                                   Model model){
        Pageable pageable = PageRequest.of(pageIndex,pageSize);
        Page<Article> page = articleService.listAllArticle(pageable);
        List<Article> list = page.getContent();
        model.addAttribute("page",page);
        model.addAttribute("articleList",list);
        return new ModelAndView(async?"admin/article_list :: #mainContainerRepleace" : "admin/article_list",
                "articleModel",model);
    }

    @GetMapping("/edit/{id}")
    public ModelAndView modifyForm(@PathVariable("id")Long id, Model model){
        Optional<Article> article = articleService.getArticleById(id);
        model.addAttribute("article",article.get());
        return new ModelAndView("/admin/add_article","articleModel",model);
    }

    @GetMapping("/add")
    public ModelAndView addArticle(Model model){
        model.addAttribute("article",new Article(null,null,null));
        return new ModelAndView("/admin/add_article","articleModel",model);
    }

    @PostMapping
    public ResponseEntity<Response> saveOrUpdateArticle(@RequestBody JSONObject fastjson) {

        Long id = fastjson.getLong("id");
        Article article;
        if (id!=null){
            article = articleService.getArticleById(fastjson.getLong("id")).get();
            article.setTitle(fastjson.getString("title"));
            article.setHtmlContent(fastjson.getString("htmlContent"));
            article.setAuthor(fastjson.getString("author"));
            article.setaCatalog(aCatalogService.findCatalogById(fastjson.getInteger("cid")).get());
        }else {
            article = new Article(fastjson.getString("author"), fastjson.getString("title"), fastjson.getString("htmlContent"));
            article.setaCatalog(aCatalogService.findCatalogById(fastjson.getInteger("cid")).get());
        }
        articleService.saveArticle(article);
        return ResponseEntity.ok().body(new Response(true,"创建文章成功",null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id")Long id){
        try{
            articleService.removeArticle(id);
        }catch (Exception e){
            return ResponseEntity.ok().body(new Response(false,e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true,"删除成功!"));
    }
}
