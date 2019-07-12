package sales.salesmen.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sales.salesmen.entity.Article;
import sales.salesmen.entity.Comment;
import sales.salesmen.entity.User;
import sales.salesmen.repository.ArticleRepository;
import sales.salesmen.repository.UserRepository;
import sales.salesmen.service.ArticleService;

import javax.servlet.annotation.ServletSecurity;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Article saveArticle(Article article) {
        Article rarticle = articleRepository.save(article);
        return rarticle;
    }

    @Override
    public void removeArticle(Long id) {
        articleRepository.deleteById(id);
    }

    @Override
    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }

    @Override
    public Article createComment(Long articleId,String content) {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        Article origionalArticle = null;
        if (optionalArticle.isPresent()){
            origionalArticle = optionalArticle.get();
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Comment comment = new Comment(content,user);
            origionalArticle.addComment(comment);
        }
        return origionalArticle;
    }

    @Override
    public Page<Article> listAllArticle(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }
}
