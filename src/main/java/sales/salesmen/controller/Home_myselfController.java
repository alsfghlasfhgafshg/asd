package sales.salesmen.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sales.salesmen.entity.Course;
import sales.salesmen.entity.User;
import sales.salesmen.repository.CourseRepository;
import sales.salesmen.repository.CourseStarRepository;
import sales.salesmen.service.ArticleLookedUserService;

import java.util.List;

@Controller
public class Home_myselfController {

    @Autowired
    CourseStarRepository courseStarRepository;

    @Autowired
    CourseRepository courseRepository;


    @Autowired
    ArticleLookedUserService articleLookedUserService;

    @GetMapping("/myself/myclient")
    public String myclient(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken,
                           Model model) {

        Long uid = ((User) usernamePasswordAuthenticationToken.getPrincipal()).getId();

        List<User> allmyclient = articleLookedUserService.allmyclient(uid);
        model.addAttribute("allmyclient", allmyclient);

        return "home/myself/myclient";
    }


    @GetMapping("/myself/mystar")
    public String mystar(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken,
                         Model model) {
        if(usernamePasswordAuthenticationToken==null){
            Long uid = ((User) usernamePasswordAuthenticationToken.getPrincipal()).getId();

            List<Long> uids = courseStarRepository.allStarCoursesid(uid);
            List<Course> allcourse = courseRepository.findAllByIdIn(uids);

            model.addAttribute("allcourse", allcourse);

        }else {
            model.addAttribute("allcourse", null);

        }

        Long uid = ((User) usernamePasswordAuthenticationToken.getPrincipal()).getId();

        List<Long> uids = courseStarRepository.allStarCoursesid(uid);
        List<Course> allcourse = courseRepository.findAllByIdIn(uids);

        model.addAttribute("allcourse", allcourse);

        return "home/myself/star";
    }
}
