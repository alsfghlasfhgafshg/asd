package sales.salesmen.repository.es;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import sales.salesmen.esentity.EsServing;

public interface EsServingRepository extends ElasticsearchRepository<EsServing,String> {
    Page<EsServing> findByTitleContainingOrSubtitleContainingOrSummaryContaining(String title, String subtitle,String summary, Pageable pageable);
    EsServing findByServingId(Long id);
}
