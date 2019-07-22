package sales.salesmen.repository.es;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import sales.salesmen.esentity.EsArticle;

public interface EsArticleRepository extends ElasticsearchRepository<EsArticle,String> {
    Page<EsArticle> findByTitleContainingOrAuthorContainingOrContentContaining(
            String title, String author, String content, Pageable pageable);

    EsArticle findByArticleId(Long articleId);
}
