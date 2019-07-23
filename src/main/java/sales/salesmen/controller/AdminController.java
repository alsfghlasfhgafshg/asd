package sales.salesmen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sales.salesmen.vo.Menu;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/admins")
public class AdminController {

    @GetMapping
    public ModelAndView listUsers(Model model, Principal principal) {
        List<Menu> list = new ArrayList<>();
        list.add(new Menu("用户管理", "/admins/users", "users"));
        list.add(new Menu("文章管理", "/admins/articles", "articles"));
        list.add(new Menu("产品管理", "/admins/products", "products"));
        list.add(new Menu("服务管理", "/admins/serving", "serving"));
        list.add(new Menu("课程管理", "/admins/courses", "courses"));
        list.add(new Menu("轮播图管理", "/admins/carousel", "carousel"));

        model.addAttribute("list", list);
        return new ModelAndView("admin/index", "menuList", model);
    }

}
