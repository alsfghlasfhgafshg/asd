package sales.salesmen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sales.salesmen.entity.CCatalog;

public interface CCatalogRepository extends JpaRepository<CCatalog,Integer> {
}
