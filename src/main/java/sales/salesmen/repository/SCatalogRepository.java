package sales.salesmen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sales.salesmen.entity.SCatalog;

public interface SCatalogRepository  extends JpaRepository<SCatalog,Integer> {
}
