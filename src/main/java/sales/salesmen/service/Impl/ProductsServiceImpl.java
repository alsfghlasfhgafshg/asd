package sales.salesmen.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sales.salesmen.entity.PCatalog;
import sales.salesmen.entity.Products;
import sales.salesmen.esentity.EsProduct;
import sales.salesmen.repository.ProductsRepository;
import sales.salesmen.service.EsProductService;
import sales.salesmen.service.ProductsService;

import java.util.Optional;

@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private EsProductService esProductService;

    @Override
    public Page<Products> listProducts(Pageable pageable) {
        return productsRepository.findAll(pageable);
    }

    @Override
    public Optional<Products> findProductById(Long id) {
        return productsRepository.findById(id);
    }

    @Override
    public Products saveProducts(Products products) {
        boolean isNew = (products.getId()==null);
        Products rproducts = null;
        EsProduct esProduct = null;
        rproducts = productsRepository.save(products);
        if (isNew){
            esProduct = new EsProduct(products);
        }else {
            esProduct = esProductService.getEsProductByProductId(products.getId());
            esProduct.update(rproducts);
        }
        esProductService.updateEsProduct(esProduct);
        return rproducts;
    }

    @Override
    public void removeProductById(Long id) {
        productsRepository.deleteById(id);
        EsProduct esProduct = esProductService.getEsProductByProductId(id);
        esProductService.removeEsProduct(esProduct.getId());
    }

    @Override
    public Page<Products> listAllByPcatalog(PCatalog pCatalog, Pageable pageable) {
        return productsRepository.findAllByPCatalog(pCatalog,pageable);
    }
}
