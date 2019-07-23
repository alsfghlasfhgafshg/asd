package sales.salesmen.controller;

import com.alibaba.fastjson.JSONObject;
import org.hibernate.collection.internal.PersistentBag;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sales.salesmen.entity.Authority;
import sales.salesmen.entity.Carousel;
import sales.salesmen.entity.Course;
import sales.salesmen.entity.User;
import sales.salesmen.repository.AuthorityRepository;
import sales.salesmen.repository.CarouselRepository;
import sales.salesmen.repository.UserRepository;
import sales.salesmen.service.CourseService;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/course")
public class Home_courseController {

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    CarouselRepository carouselRepository;

    @Autowired
    CourseService courseService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/all")
    public @ResponseBody
    List<Course> getall(@RequestParam("page") int page) {
        return courseService.getAllCourseByccatalog12(1, 1, page);
    }

    @GetMapping("/{courseid}")
    public String coursepage(@PathVariable long courseid, Model model, UsernamePasswordAuthenticationToken principal) {
        Course course = courseService.getCourseById(courseid);
        course.setSumplay(course.getSumplay() + 1);
        courseService.saveOrUpdate(course);
        boolean isstar = false;

        model.addAttribute("course", course);
        model.addAttribute("ccatalog", course.getcCatalog().getName());

        Long userid = null;
        if (principal != null) {
            userid = ((User) principal.getPrincipal()).getId();
            if (courseService.isStar(courseid, userid) == true) {
                isstar = true;
            }
        }

        model.addAttribute("isstar", isstar);
        return "/page/course_item";
    }

    @GetMapping
    public String course(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken, Model model) {
        Authority authority = null;

        if (usernamePasswordAuthenticationToken == null) {
            authority = authorityRepository.findById(3L).get();
        } else {
            for (GrantedAuthority usernamePasswordAuthenticationTokenAuthority : usernamePasswordAuthenticationToken.getAuthorities()) {
                if (usernamePasswordAuthenticationTokenAuthority.getAuthority().equals("ROLE_SALES")) {
                    authority = authorityRepository.findById(2L).get();
                } else {
                    authority = authorityRepository.findById(3L).get();
                }
            }
        }
        List<Carousel> carousels = new ArrayList<>();

        for (Carousel carousel : carouselRepository.findAllByCourseNotNull()) {
            if (carousel.getCourse().getcCatalog().getAuthority().getId() == authority.getId()) {
                carousels.add(carousel);
            }
        }

        model.addAttribute("carousels", carousels);
        return "page/course";
    }

    @GetMapping("/getapage")
    public @ResponseBody
    List<Course> getapage(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                          @RequestParam(value = "ccatalog", required = false, defaultValue = "0") int ccatalog,
                          @RequestParam(value = "ccatalog2", required = false, defaultValue = "0") int ccatalog2) {
        List<Course> allCourseByccatalog12 = courseService.getAllCourseByccatalog12(ccatalog, ccatalog2, page);
        for (Course course : allCourseByccatalog12) {
            course.setUsers(null);
        }
        return allCourseByccatalog12;
    }

    @PostMapping("/starcourse")
    public @ResponseBody
    JSONObject starcourse(@RequestParam("courseid") int courseid,
                          UsernamePasswordAuthenticationToken principal) {
        JSONObject responsejson = new JSONObject();


        if (principal != null) {
            courseService.statCourse(courseid, ((User) principal.getPrincipal()).getId());
            responsejson.put("error", 0);
            responsejson.put("isok", true);
        } else {
            responsejson.put("error", 0);
            responsejson.put("errmsg", "验证失败");
            responsejson.put("isok", false);
        }
        return responsejson;

    }

    @PostMapping("/disstarcourse")
    public @ResponseBody
    JSONObject disStarcourse(@RequestParam("courseid") int courseid,
                             UsernamePasswordAuthenticationToken principal) {
        JSONObject responsejson = new JSONObject();


        if (principal != null) {
            courseService.disStarCourse(courseid, ((User) principal.getPrincipal()).getId());
            responsejson.put("error", 0);
            responsejson.put("isok", true);
        } else {
            responsejson.put("error", 0);
            responsejson.put("errmsg", "验证失败");
            responsejson.put("isok", false);
        }
        return responsejson;
    }

    @PostMapping("/test")
    public @ResponseBody
    JSONObject dsaf() {
        return null;
    }


}
