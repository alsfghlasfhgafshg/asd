package sales.salesmen.service;

import sales.salesmen.entity.Article;
import sales.salesmen.entity.User;

import java.util.Optional;

public interface ArticleService {
    Article saveArticle(Article article);
    void removeArticle(Long id);
    Optional<Article> getArticleById(Long id);
    Article createComment(Long articleId,String content);
}
