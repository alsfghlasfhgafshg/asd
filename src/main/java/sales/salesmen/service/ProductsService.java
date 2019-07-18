package sales.salesmen.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sales.salesmen.entity.PCatalog;
import sales.salesmen.entity.Products;

import java.util.Optional;

public interface ProductsService {
    Page<Products> listProducts(Pageable pageable);
    Optional<Products> findProductById(Long id);
    Products saveProducts(Products products);
    void removeProductById(Long id);
    Page<Products> listAllByPcatalog(PCatalog pCatalog,Pageable pageable);
}
