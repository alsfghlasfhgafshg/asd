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
import sales.salesmen.service.EsArticleService;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private EsArticleService esArticleService;

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
}
