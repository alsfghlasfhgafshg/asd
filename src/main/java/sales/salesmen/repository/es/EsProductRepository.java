package sales.salesmen.repository.es;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import sales.salesmen.esentity.EsProduct;

public interface EsProductRepository extends ElasticsearchRepository<EsProduct, String> {
    Page<EsProduct> findByNameContainingOrTypeContaining(String name, String type, Pageable pageable);
    EsProduct findByProductId(Long id);

}
