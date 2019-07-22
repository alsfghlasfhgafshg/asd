package sales.salesmen.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sales.salesmen.esentity.EsArticle;

public interface EsArticleService {
    void removeEsArticle(String id);
    EsArticle updateEsArticle(EsArticle esArticle);
    EsArticle getEsArticleByArticleId(Long articleId);
    Page<EsArticle> listEsArticle(Pageable pageable);
    Page<EsArticle> listNewestEsArticles(String keyword,Pageable pageable);
}
