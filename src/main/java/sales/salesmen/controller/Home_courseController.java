package sales.salesmen.controller;

import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sales.salesmen.entity.Course;
import sales.salesmen.repository.CourseRepository;
import sales.salesmen.service.CourseService;

import java.util.List;


@Controller
@RequestMapping("/course")
public class Home_courseController {


    @Autowired
    CourseService courseService;

    @GetMapping("/all")
    public @ResponseBody
    List<Course> getall(@RequestParam("page") int page) {
        return courseService.getAllCourseByccatalog12(1, 1, page);
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
        return courseService.getAllCourseByccatalog12(ccatalog, ccatalog2, page);

    }


}
