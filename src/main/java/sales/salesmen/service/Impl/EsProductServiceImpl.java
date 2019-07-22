package sales.salesmen.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sales.salesmen.esentity.EsProduct;
import sales.salesmen.repository.es.EsProductRepository;
import sales.salesmen.service.EsProductService;

@Service
public class EsProductServiceImpl implements EsProductService {

    @Autowired
    private EsProductRepository esProductRepository;

    @Override
    public void removeEsProduct(String id) {
        esProductRepository.deleteById(id);
    }

    @Override
    public EsProduct updateEsProduct(EsProduct esProduct) {
        return esProductRepository.save(esProduct);
    }

    @Override
    public EsProduct getEsProductByProductId(Long productId) {
        return esProductRepository.findByProductId(productId);
    }

    @Override
    public Page<EsProduct> listEsProduct(Pageable pageable) {
        return esProductRepository.findAll(pageable);
    }

    @Override
    public Page<EsProduct> listNewestEsProducts(String keyword, Pageable pageable) {
        Page<EsProduct> pages = null;
        Sort sort = new Sort(Sort.Direction.DESC,"productId");
        if (pageable.getSort().isUnsorted()){
            pageable = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(),sort);
        }
        pages = esProductRepository.findByNameContainingOrTypeContaining(keyword,keyword,pageable);
        return pages;
    }
}
