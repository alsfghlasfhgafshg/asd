package sales.salesmen.controller;


import com.alibaba.fastjson.JSONObject;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sales.salesmen.entity.*;
import sales.salesmen.repository.*;
import sales.salesmen.service.FileService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admins/carousel")
public class Admin_carouselController {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    CCatalogRepository cCatalogRepository;

    @Autowired
    CarouselRepository carouselRepository;

    @Autowired
    FileService fileService;

    @GetMapping("/model")
    public @ResponseBody
    JSONObject model() {

        JSONObject responsejson = new JSONObject();

        List<Article> articles = articleRepository.findAll(
                PageRequest.of(0, 10, new Sort(Sort.Direction.DESC, "createTime"))).getContent();

        List<CCatalog> saleccatalog = cCatalogRepository.findByAuthority(authorityRepository.findById((long) 3).get());
        List<CCatalog> customccatalog = cCatalogRepository.findByAuthority(authorityRepository.findById((long) 2).get());

        List<Course> salescourses = courseRepository.findAllByCCatalogIn(
                saleccatalog, PageRequest.of(0, 10, new Sort(Sort.Direction.DESC, "id"))).getContent();

        List<Course> customcourses = courseRepository.findAllByCCatalogIn(
                customccatalog, PageRequest.of(0, 10, new Sort(Sort.Direction.DESC, "id"))).getContent();

        List<Carousel> carousels = carouselRepository.findAll();

        List<Carousel> articlecarousels = new ArrayList<>();
        List<Carousel> salecoursecarousels = new ArrayList<>();
        List<Carousel> customcoursecarousels = new ArrayList<>();



        for (Carousel carousel : carousels) {
            if (carousel.getArticle() != null) {
                articlecarousels.add(carousel);
            } else {
                if (carousel.getCourse() != null) {
                    if (carousel.getCourse().getcCatalog().getAuthority().getId() == 3) {
                        customcoursecarousels.add(carousel);
                    } else {
                        salecoursecarousels.add(carousel);
                    }
                }
            }
        }

        for (Course salescours : salescourses) {
            salescours.setUsers(null);
        }
        for (Course customcours : customcourses) {
            customcours.setUsers(null);
        }

        for (Carousel salecoursecarousel : salecoursecarousels) {
            salecoursecarousel.getCourse().setUsers(null);
        }
        for (Carousel customcoursecarousel : customcoursecarousels) {
            customcoursecarousel.getCourse().setUsers(null);
        }


        responsejson.put("articlecarousels", articlecarousels);
        responsejson.put("salecoursecarousels", salecoursecarousels);
        responsejson.put("customcoursecarousels", customcoursecarousels);

        responsejson.put("articles", articles);
        responsejson.put("salescourses", salescourses);
        responsejson.put("customcourses", customcourses);


        return responsejson;
    }

    @GetMapping
    public String index(Model model) {


        return "/admin/carousel";
    }

    @GetMapping("/delcarousel")
    public String delarticlecarousel(@RequestParam("itemid") int itemid) {
        carouselRepository.deleteById(itemid);
        return "redirect:/admins";
    }

    @PostMapping("/addarticlecarousel")
    public String addarticlecarousel(@RequestParam("pic") MultipartFile file, @RequestParam("articleid") int articleid) {

        Carousel c = new Carousel();
        c.setArticle(articleRepository.findById((long) articleid).get());
        c.setPicPath(fileService.uploadImage(file));

        carouselRepository.save(c);
        return "redirect:/admins";
    }

    @PostMapping("/addcustomcoursecarousel")
    public String addcustomcourcecarousel(@RequestParam("pic") MultipartFile file, @RequestParam("articleid") long courseid) {

        Carousel c = new Carousel();
        c.setCourse(courseRepository.findById(courseid).get());
        c.setPicPath(fileService.uploadImage(file));

        carouselRepository.save(c);
        return "redirect:/admins";
    }

    @PostMapping("/addsalecoursecarousel")
    public String addsalecourcecarousel(@RequestParam("pic") MultipartFile file, @RequestParam("articleid") long courseid) {

        Carousel c = new Carousel();
        c.setCourse(courseRepository.findById(courseid).get());
        c.setPicPath(fileService.uploadImage(file));

        carouselRepository.save(c);
        return "redirect:/admins";
    }


}
