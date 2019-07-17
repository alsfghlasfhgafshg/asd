package sales.salesmen.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sales.salesmen.entity.ACatalog;
import sales.salesmen.entity.Article;
import sales.salesmen.entity.User;

import java.util.Optional;

public interface ArticleService {
    Article saveArticle(Article article);
    void removeArticle(Long id);
    Optional<Article> getArticleById(Long id);
    Article createComment(Long articleId,String content);
    Page<Article> listAllArticle(Pageable pageable);
    Page<Article> listByACatalog(ACatalog aCatalog,Pageable pageable);
    void removeComment(Long articleId,Long commentId);
}
