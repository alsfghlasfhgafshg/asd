package sales.salesmen.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import sales.salesmen.entity.Products;

public interface ProductsRepository extends JpaRepository<Products,Long> {
}
