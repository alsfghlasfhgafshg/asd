package sales.salesmen.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sales.salesmen.esentity.EsServing;

public interface EsServingService {
    void removeEsServing(String id);
    EsServing updateEsServing(EsServing esServing);
    EsServing getEsServingByServingId(Long servingId);
    Page<EsServing> listEsServing(Pageable pageable);
    Page<EsServing> listNewestEsServing(String keyword,Pageable pageable);

}
