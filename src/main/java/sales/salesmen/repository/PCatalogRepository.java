package sales.salesmen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sales.salesmen.entity.PCatalog;

public interface PCatalogRepository extends JpaRepository<PCatalog,Integer> {
}
