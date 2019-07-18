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
import sales.salesmen.entity.*;
import sales.salesmen.service.ACatalogService;
import sales.salesmen.service.ArticleService;
import sales.salesmen.service.PCatalogService;
import sales.salesmen.service.ProductsService;
import sales.salesmen.utils.TimeCompareUtil;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ACatalogService aCatalogService;

    @Autowired
    private PCatalogService pCatalogService;

    @Autowired
    private ProductsService productsService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/home")
    public ModelAndView home(@RequestParam(value = "async",required = false)boolean async,
                             @RequestParam(value = "pageIndex",required = false,defaultValue = "0")int pageIndex,
                             @RequestParam(value = "pageSize",required = false,defaultValue = "10")int pageSize,
                             @RequestParam(value = "catalog",required = false)Integer catalog,
                             Model model){
        Page<Article> page;
        List<Article> list;
        if (catalog!=null&&catalog>0){
            ACatalog aCatalog = aCatalogService.findCatalogById(catalog).get();
            Pageable pageable = PageRequest.of(pageIndex,pageSize);
            page = articleService.listByACatalog(aCatalog,pageable);
            list = page.getContent();
        }
        else {
            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            page = articleService.listAllArticle(pageable);
            list = page.getContent();
        }
        Date date = new Date();
        TimeCompareUtil timeCompareUtil = new TimeCompareUtil();
        for (Article article:list){
            article.setTimeDifference(timeCompareUtil.timeDifference(date,article.getCreateTime()));
        }
        model.addAttribute("articleList",list);
        return new ModelAndView(async?"page/home :: #articless" : "page/home","articleModel",model);
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
    public ModelAndView productservice(@RequestParam(value = "async",required = false)boolean async,
                                       @RequestParam(value = "pageIndex",required = false,defaultValue = "0")int pageIndex,
                                       @RequestParam(value = "pageSize",required = false,defaultValue = "10")int pageSize,
                                       @RequestParam(value = "catalog",required = false)Integer catalog,
                                       Model model) {
        Page<Products> page;
        List<Products> list;
        Pageable pageable = PageRequest.of(pageIndex,pageSize);
        if (catalog!=null&&catalog>0){//如果有分类
            PCatalog pCatalog = pCatalogService.findCatalogById(catalog).get();
            page = productsService.listAllByPcatalog(pCatalog,pageable);
            list = page.getContent();
        }
        else {//没分类的时候
            page = productsService.listProducts(pageable);
            list = page.getContent();
        }
        model.addAttribute("list",list);
        return new ModelAndView(async?"page/productservice :: #productContainer":"page/productservice","productModel",model);
    }


}
