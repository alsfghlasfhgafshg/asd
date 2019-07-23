package sales.salesmen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sales.salesmen.entity.Authority;
import sales.salesmen.entity.CCatalog;

import java.util.List;

public interface CCatalogRepository extends JpaRepository<CCatalog, Integer> {
    List<CCatalog> findByAuthority(Authority authority0);
}
