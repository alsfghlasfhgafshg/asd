package sales.salesmen.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sales.salesmen.entity.Article;
import sales.salesmen.entity.SCatalog2;
import sales.salesmen.entity.Serving;

public interface ServingRepository extends JpaRepository<Serving, Long> {
    Page<Serving> findAllBySCatalog2(Pageable pageable, SCatalog2 sCatalog2);
}
