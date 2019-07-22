package sales.salesmen.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sales.salesmen.esentity.EsProduct;

public interface EsProductService {
    void removeEsProduct(String id);
    EsProduct updateEsProduct(EsProduct esProduct);
    EsProduct getEsProductByProductId(Long productId);
    Page<EsProduct> listEsProduct(Pageable pageable);
    Page<EsProduct> listNewestEsProducts(String keyword,Pageable pageable);
}
