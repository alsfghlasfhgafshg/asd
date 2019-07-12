package sales.salesmen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sales.salesmen.entity.ACatalog;

public interface ACatalogRepository extends JpaRepository<ACatalog,Integer> {
}
