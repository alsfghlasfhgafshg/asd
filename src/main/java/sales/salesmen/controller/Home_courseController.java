package sales.salesmen.controller;

import com.alibaba.fastjson.JSONObject;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sales.salesmen.entity.Course;
import sales.salesmen.entity.User;
import sales.salesmen.repository.UserRepository;
import sales.salesmen.service.CourseService;

import java.util.List;


@Controller
@RequestMapping("/course")
public class Home_courseController {


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
    public String course() {
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
