package sales.salesmen.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sales.salesmen.esentity.EsServing;
import sales.salesmen.repository.es.EsServingRepository;
import sales.salesmen.service.EsServingService;

@Service
public class EsServingServiceImpl implements EsServingService {

    @Autowired
    private EsServingRepository esServingRepository;

    @Override
    public void removeEsServing(String id) {
        esServingRepository.deleteById(id);
    }

    @Override
    public EsServing updateEsServing(EsServing esServing) {
        return esServingRepository.save(esServing);
    }

    @Override
    public EsServing getEsServingByServingId(Long servingId) {
        return esServingRepository.findByServingId(servingId);
    }

    @Override
    public Page<EsServing> listEsServing(Pageable pageable) {
        return esServingRepository.findAll(pageable);
    }

    @Override
    public Page<EsServing> listNewestEsServing(String keyword, Pageable pageable) {
        Page<EsServing> pages = null;
        Sort sort = new Sort(Sort.Direction.DESC,"servingId");
        if (pageable.getSort().isUnsorted()){
            pageable = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(),sort);
        }
        pages = esServingRepository.findByTitleContainingOrSubtitleContainingOrSummaryContaining(
                    keyword, keyword, keyword, pageable);
        return pages;
    }
}
