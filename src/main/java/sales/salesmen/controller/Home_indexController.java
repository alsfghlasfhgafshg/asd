package sales.salesmen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sales.salesmen.entity.Article;
import sales.salesmen.entity.Comment;
import sales.salesmen.entity.User;
import sales.salesmen.service.ArticleService;
import sales.salesmen.service.CommentService;
import sales.salesmen.vo.Response;

import java.util.List;

@Controller
@RequestMapping("/home")
public class Home_indexController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/article/{id}")
    public String viewArticle(@PathVariable("id")Long id, Model model){

        Article article = articleService.getArticleById(id).get();
        model.addAttribute("articleModel",article);
        return "/home/home_article";
    }

    @GetMapping("/article/comments")
    public String viewComments(@RequestParam("articleId")Long articleId,Model model){
        Article article = articleService.getArticleById(articleId).get();
        List<Comment> comments = article.getComments();
        String commentOwner="";
        if (SecurityContextHolder.getContext().getAuthentication()!=null&&SecurityContextHolder.getContext().getAuthentication().isAuthenticated()&&
                !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")){
            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal!=null){
                commentOwner = principal.getUsername();
            }
        }
        model.addAttribute("commentOwner",commentOwner);
        model.addAttribute("comments",comments);
        return "/home/home_article :: #mainContainerRepleace";
    }

    @PostMapping("/article/comments")
    public ResponseEntity<Response> subComments(Long articleId, String commentContent){
        try {
            articleService.createComment(articleId, commentContent);
        }catch (Exception e){
            return ResponseEntity.ok().body(new Response(false,e.toString()));
        }
        return ResponseEntity.ok().body(new Response(true,"发布成功",null));
    }

    @DeleteMapping("/article/comments/{commentId}")
    public ResponseEntity<Response> removeComment(@PathVariable("commentId")Long commentId,@RequestParam("articleId")Long articleId){
        try {
            articleService.removeComment(articleId, commentId);
            commentService.removeCommentById(commentId);
        }catch (Exception e){
            return ResponseEntity.ok().body(new Response(false,e.toString()));
        }
        return ResponseEntity.ok().body(new Response(true,"删除成功",null));
    }
}
