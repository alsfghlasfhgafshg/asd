package sales.salesmen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sales.salesmen.entity.Authority;
import sales.salesmen.entity.User;
import sales.salesmen.service.AuthorityService;
import sales.salesmen.service.UserService;
import sales.salesmen.vo.Response;

import javax.validation.ConstraintViolationException;
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
        return new ModelAndView(async?"admin/list :: #mainContainerRepleace" : "admin/list","userModel",model);
    }

    @GetMapping("/edit/{id}")
    public ModelAndView modifyForm(@PathVariable("id")Long id, Model model){
        Optional<User> user = userService.getUserById(id);
        model.addAttribute("user",user.get());
        return new ModelAndView("admin/edit","userModel",model);
    }

    @PostMapping
    public ResponseEntity<Response> saveOrUpdateUser(@RequestParam("disableds")String disabled, User user, Long authorityId){

        if (disabled.equals("是")){
            user.setDisabled(true);
        }
        User presentUser=null;
        Optional<User> origionalUser = userService.getUserById(user.getId());
        if (origionalUser.isPresent()){
            presentUser = origionalUser.get();
            user.setCreatetime(presentUser.getCreatetime());
            user.setPassword(presentUser.getPassword());
        }
        List<Authority> list = new ArrayList<>();
        list.add(authorityService.getAuthorityById(authorityId).get());
        user.setAuthorities(list);

        try{
            userService.SaveOrUpdateUser(user);
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
}
