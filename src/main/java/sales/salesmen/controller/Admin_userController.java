package sales.salesmen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sales.salesmen.entity.Authority;
import sales.salesmen.entity.User;
import sales.salesmen.service.AuthorityService;
import sales.salesmen.service.UserService;
import sales.salesmen.vo.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class Admin_userController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;

    @GetMapping
    public ModelAndView getUser(@RequestParam(value = "async",required = false)boolean async,
                                @RequestParam(value = "pageIndex",required = false,defaultValue = "0")int pageIndex,
                                @RequestParam(value = "pageSize",required = false,defaultValue = "10")int pageSize,
                                @RequestParam(value = "username",required = false,defaultValue = "")String username,
                                Model model){
        Pageable pageable = PageRequest.of(pageIndex,pageSize);
        Page<User> page = userService.listUserByUsernameLike(username,pageable);
        List<User> list = page.getContent();
        model.addAttribute("page",page);
        model.addAttribute("userList",list);
        return new ModelAndView(async?"admin/user_list :: #mainContainerRepleace" : "admin/user_list","userModel",model);
    }

    @GetMapping("/edit/{id}")
    public ModelAndView modifyForm(@PathVariable("id")Long id, Model model){
        Optional<User> user = userService.getUserById(id);
        model.addAttribute("user",user.get());
        return new ModelAndView("admin/edit_user","userModel",model);
    }

    @PostMapping
    public ResponseEntity<Response> saveOrUpdateUser(@RequestParam("disableds")String disabled, User user, Long authorityId){

        if (disabled.equals("是")){
            user.setDisabled(true);
        }

        List<Authority> list = new ArrayList<>();
        list.add(authorityService.getAuthorityById(authorityId).get());
        user.setAuthorities(list);
        try{
            if (user.getId()==null){
                user.setEncodePassword(user.getPassword());
                userService.SaveOrUpdateUser(user);
            }else {
                User optionalUser = userService.getUserById(user.getId()).get();
                optionalUser.setAuthorities(list);
                optionalUser.setDisabled(user.isDisabled());
                optionalUser.setPhonenum(user.getPhonenum());
                userService.SaveOrUpdateUser(optionalUser);
            }
        }catch (Exception e){
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true,"处理成功",user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id")Long id){
        try{
            userService.removeUser(id);
        }catch (Exception e){
            return ResponseEntity.ok().body(new Response(false,e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true,"删除成功!"));
    }

    @GetMapping("/add")
    public ModelAndView addUser(Model model){
        model.addAttribute("user",new User(null,null,null));
        return new ModelAndView("/admin/add_user","userModel",model);
    }
}
