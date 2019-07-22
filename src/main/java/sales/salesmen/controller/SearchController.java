package sales.salesmen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sales.salesmen.esentity.EsArticle;
import sales.salesmen.esentity.EsCourse;
import sales.salesmen.esentity.EsProduct;
import sales.salesmen.esentity.EsServing;
import sales.salesmen.service.EsArticleService;
import sales.salesmen.service.EsCourseService;
import sales.salesmen.service.EsProductService;
import sales.salesmen.service.EsServingService;
import sales.salesmen.utils.TimeCompareUtil;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private EsArticleService esArticleService;
    @Autowired
    private EsProductService esProductService;
    @Autowired
    private EsServingService esServingService;
    @Autowired
    private EsCourseService esCourseService;

    @GetMapping("/esarticle")
    public ModelAndView listEsArticles(
            @RequestParam(value = "keyword",required = false,defaultValue = "")String keyword,
            @RequestParam(value = "pageIndex",required = false,defaultValue = "0")int pageIndex,
            @RequestParam(value = "pageSize",required = false,defaultValue = "10")int pageSize,
            Model model){
        Page<EsArticle> page = null;
        List<EsArticle> list = null;
        boolean isEmpty = true;
        try{
            Sort sort = new Sort(Sort.Direction.DESC,"articleId");
            Pageable pageable = PageRequest.of(pageIndex,pageSize,sort);
            page = esArticleService.listNewestEsArticles(keyword,pageable);
            isEmpty = false;
        }catch (Exception e){
            Pageable pageable = PageRequest.of(pageIndex,pageSize);
            page = esArticleService.listEsArticle(pageable);
        }
        list = page.getContent();

        model.addAttribute("articleList",list);

        return new ModelAndView("/page/home :: #articless","articleModel",model);
    }

    @GetMapping
    public String search(){return "/search";}

    @GetMapping("/esproduct")
    public ModelAndView listEsProducts(
        @RequestParam(value = "keyword", required = false, defaultValue = "")String keyword,
        @RequestParam(value = "pageIndex",required = false,defaultValue = "0")int pageIndex,
        @RequestParam(value = "pageSize",required = false,defaultValue = "10")int pageSize,
        Model model){
        Page<EsProduct> page = null;
        List<EsProduct> list = null;
        boolean isEmpty = true;
        try{
            Sort sort = new Sort(Sort.Direction.DESC,"productId");
            Pageable pageable = PageRequest.of(pageIndex,pageSize,sort);
            page = esProductService.listNewestEsProducts(keyword,pageable);
            isEmpty = false;
        }catch (Exception e){
            Pageable pageable = PageRequest.of(pageIndex,pageSize);
            page = esProductService.listEsProduct(pageable);
        }

        list = page.getContent();

        model.addAttribute("list",list);

        return new ModelAndView("/home/home_productservice :: #productContainer","productModel",model);

    }

    @GetMapping("/esserving")
    public ModelAndView listEsServing(
            @RequestParam(value = "keyword", required = false, defaultValue = "")String keyword,
            @RequestParam(value = "pageIndex",required = false,defaultValue = "0")int pageIndex,
            @RequestParam(value = "pageSize",required = false,defaultValue = "10")int pageSize,
            Model model){
        Page<EsServing> page = null;
        List<EsServing> list = null;
        boolean isEmpty = true;
        try{
            Sort sort = new Sort(Sort.Direction.DESC,"servingId");
            Pageable pageable = PageRequest.of(pageIndex,pageSize,sort);
            page = esServingService.listNewestEsServing(keyword,pageable);
            isEmpty = false;
        }catch (Exception e){
            Pageable pageable = PageRequest.of(pageIndex,pageSize);
            page = esServingService.listEsServing(pageable);
        }

        list = page.getContent();
        //下面有待更改

        model.addAttribute("list",list);

        return new ModelAndView("/home/home_productservice :: #productContainer","productModel",model);

    }

    @GetMapping("/escourse")
    public ModelAndView listEsCourse(
            @RequestParam(value = "keyword", required = false, defaultValue = "")String keyword,
            @RequestParam(value = "pageIndex",required = false,defaultValue = "0")int pageIndex,
            @RequestParam(value = "pageSize",required = false,defaultValue = "10")int pageSize,
            Model model){
        Page<EsCourse> page = null;
        List<EsCourse> list = null;
        boolean isEmpty = true;
        try{
            Sort sort = new Sort(Sort.Direction.DESC,"courseId");
            Pageable pageable = PageRequest.of(pageIndex,pageSize,sort);
            page = esCourseService.listNewestEsCourse(keyword,pageable);
            isEmpty = false;
        }catch (Exception e){
            Pageable pageable = PageRequest.of(pageIndex,pageSize);
            page = esCourseService.listEsCourse(pageable);
        }

        list = page.getContent();
        //下面有待更改+1

        model.addAttribute("list",list);

        return new ModelAndView("/home/home_productservice :: #productContainer","productModel",model);


    }
}
