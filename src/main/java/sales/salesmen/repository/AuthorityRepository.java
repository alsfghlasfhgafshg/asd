package sales.salesmen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sales.salesmen.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority,Long> {
}
