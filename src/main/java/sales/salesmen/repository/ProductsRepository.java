package sales.salesmen.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sales.salesmen.entity.PCatalog;
import sales.salesmen.entity.Products;

public interface ProductsRepository extends JpaRepository<Products,Long> {
    Page<Products> findAllByPCatalog(PCatalog pCatalog, Pageable pageable);
}
