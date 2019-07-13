package sales.salesmen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sales.salesmen.entity.SCatalog;
import sales.salesmen.entity.SCatalog2;

import java.util.List;

public interface SCatalog2Repository extends JpaRepository<SCatalog2,Integer> {
    List<SCatalog2> findBySCatalog(SCatalog sCatalog);
}
