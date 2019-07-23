package sales.salesmen.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sales.salesmen.entity.*;
import sales.salesmen.repository.CarouselRepository;
import sales.salesmen.service.*;
import sales.salesmen.utils.TimeCompareUtil;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private CarouselRepository carouselRepository;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ACatalogService aCatalogService;

    @Autowired
    private PCatalogService pCatalogService;

    @Autowired
    private ProductsService productsService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public ModelAndView home(@RequestParam(value = "async", required = false) boolean async,
                             @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                             @RequestParam(value = "catalog", required = false) Integer catalog,
                             Model model, UsernamePasswordAuthenticationToken principal) {
        Page<Article> page;
        List<Article> list;
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        if (catalog != null && catalog > 0) {
            ACatalog aCatalog = aCatalogService.findCatalogById(catalog).get();
            page = articleService.listByACatalog(aCatalog, pageable);
            list = page.getContent();
        } else {
            boolean isUser = false;
            ACatalog aCatalog;
            if (principal != null) {
                for (GrantedAuthority authority : principal.getAuthorities()) {
                    if (authority.getAuthority().equals("ROLE_USER")) isUser = true;
                }
            }
            if (isUser || principal == null) {
                aCatalog = aCatalogService.findCatalogById(6).get();
            } else {
                aCatalog = aCatalogService.findCatalogById(1).get();
            }
            page = articleService.listByACatalog(aCatalog, pageable);
            list = page.getContent();

        }
        Date date = new Date();
        TimeCompareUtil timeCompareUtil = new TimeCompareUtil();
        for (Article article : list) {
            article.setTimeDifference(timeCompareUtil.timeDifference(date, article.getCreateTime()));
        }
        model.addAttribute("articleList", list);

        List<Carousel> carousels = carouselRepository.findAllByArticleNotNull();
        model.addAttribute("carousels", carousels);

        return new ModelAndView(async ? "page/home :: #articless" : "page/home", "articleModel", model);
    }

    @GetMapping("/myself")
    public String myself(Principal principal, Model model) {
        String avatarimg = null;
        if (principal != null) {
            avatarimg = ((User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getAvatar();
        }

        if (avatarimg == null) {
            avatarimg = "/img/defaultavatar.png";
        }

        if (principal == null) {
            model.addAttribute("avatarimg", "/img/defaultavatar.png");
        } else {
            model.addAttribute("avatarimg", avatarimg);
        }

        return "page/myself";
    }

    @GetMapping("/productservice")
    public ModelAndView productservice(@RequestParam(value = "async", required = false) boolean async,
                                       @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                       @RequestParam(value = "catalog", required = false) Integer catalog,
                                       Model model) {
        Page<Products> page;
        List<Products> list;
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        if (catalog != null && catalog > 0) {//如果有分类
            PCatalog pCatalog = pCatalogService.findCatalogById(catalog).get();
            page = productsService.listAllByPcatalog(pCatalog, pageable);
            list = page.getContent();
        } else {//没分类的时候
            PCatalog pCatalog = pCatalogService.findCatalogById(1).get();
            page = productsService.listAllByPcatalog(pCatalog, pageable);
            list = page.getContent();
        }
        model.addAttribute("list", list);
        return new ModelAndView(async ? "home/home_productservice :: #productContainer" : "home/home_productservice", "productModel", model);
    }


}
