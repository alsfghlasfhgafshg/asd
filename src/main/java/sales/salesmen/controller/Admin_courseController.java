package sales.salesmen.controller;


import com.alibaba.fastjson.JSONObject;
import io.netty.handler.codec.json.JsonObjectDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sales.salesmen.entity.CCatalog;
import sales.salesmen.entity.CCatalog2;
import sales.salesmen.entity.Course;
import sales.salesmen.service.CourseService;

import java.util.List;

@Controller
@RequestMapping("/admins/courses")
public class Admin_courseController {

    @Autowired
    CourseService courseService;

    @GetMapping
    public String indexpage(Model model) {
        model.addAttribute("courses", courseService.getAllCourse());
        return "/admin/course_list";
    }

    @GetMapping("/getccatalog")
    public @ResponseBody
    List<CCatalog> getallccatalog() {
        return courseService.getAllCCatalog();
    }

    @GetMapping("/getccatalog2")
    public @ResponseBody
    List<CCatalog2> getallccatalog2() {
        return courseService.getAllCCatalog2();
    }

    @PostMapping("/addcourse")
    public @ResponseBody
    JSONObject addCource(@RequestParam(value = "courseid", required = false) Long courseid,
                         @RequestParam("catalogid") int catalogid, @RequestParam("catalog2id") int catalog2id,
                         @RequestParam("title") String title, @RequestParam("teacher") String teacher,
                         @RequestParam("pic") MultipartFile pic, @RequestParam("src") MultipartFile src,
                         @RequestParam("summary") String summary) {

        JSONObject responsejson = new JSONObject();
        boolean result = courseService.saveOrUpdate(courseid, catalogid, catalog2id, title, teacher, pic, src, summary);
        if (result == true) {
            responsejson.put("error", 0);
        }
        return responsejson;
    }

    @PostMapping("/changecourse")
    public @ResponseBody
    JSONObject cahngeCource(@RequestParam(value = "courseid", required = false) Long courseid,
                         @RequestParam("catalogid") int catalogid, @RequestParam("catalog2id") int catalog2id,
                         @RequestParam("title") String title, @RequestParam("teacher") String teacher,
                         @RequestParam(value = "pic",required = false) MultipartFile pic, @RequestParam(value = "src",required = false) MultipartFile src,
                         @RequestParam("summary") String summary) {

        JSONObject responsejson = new JSONObject();
        boolean result = courseService.saveOrUpdate(courseid, catalogid, catalog2id, title, teacher, pic, src, summary);
        if (result == true) {
            responsejson.put("error", 0);
        }
        return responsejson;
    }

    @PostMapping("/delete")
    public @ResponseBody
    JSONObject deleteCourse(@RequestParam(value = "courseid") Long courseid) {
        JSONObject responsejson = new JSONObject();
        if (courseService.deleteCourse(courseid) == true) {
            responsejson.put("error", 0);
        }
        return responsejson;
    }

    @GetMapping("/getcourse")
    public @ResponseBody
    String getcource(@RequestParam("courseid") Long courseid){
        return JSONObject.toJSONString(courseService.getCourseById(courseid));
    }

}
