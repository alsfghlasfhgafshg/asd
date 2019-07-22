package sales.salesmen.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sales.salesmen.entity.ACatalog;
import sales.salesmen.entity.Article;
import sales.salesmen.entity.Comment;
import sales.salesmen.entity.User;
import sales.salesmen.esentity.EsArticle;
import sales.salesmen.repository.ArticleRepository;
import sales.salesmen.repository.UserRepository;
import sales.salesmen.service.ArticleService;
import sales.salesmen.service.EsArticleService;

import javax.servlet.annotation.ServletSecurity;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private EsArticleService esArticleService;
    @Autowired
    private ArticleRepository articleRepository;

    @Transactional
    @Override
    public Article saveArticle(Article article) {
        boolean isNew = (article.getId()==null);
        Article rarticle=null;
        EsArticle esArticle = null;
        rarticle = articleRepository.save(article);
        if (isNew){
            esArticle = new EsArticle(article);
        }else {
            esArticle = esArticleService.getEsArticleByArticleId(article.getId());
            esArticle.update(rarticle);
        }
        esArticleService.updateEsArticle(esArticle);
        return rarticle;
    }

    @Override
    public void removeArticle(Long id) {
        articleRepository.deleteById(id);
        EsArticle esArticle = esArticleService.getEsArticleByArticleId(id);
        esArticleService.removeEsArticle(esArticle.getId());
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
        return this.saveArticle(origionalArticle);
    }

    @Override
    public Page<Article> listAllArticle(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    @Override
    public Page<Article> listByACatalog(ACatalog aCatalog, Pageable pageable) {
        return articleRepository.findAllByACatalog(aCatalog,pageable);
    }

    @Override
    public void removeComment(Long articleId, Long commentId) {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if (optionalArticle.isPresent()){
            Article origionalArticle = optionalArticle.get();
            origionalArticle.removeComment(commentId);
            this.saveArticle(origionalArticle);
        }
    }
}
