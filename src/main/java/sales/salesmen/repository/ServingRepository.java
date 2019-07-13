package sales.salesmen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sales.salesmen.entity.Article;
import sales.salesmen.entity.Serving;

public interface ServingRepository extends JpaRepository<Serving,Long> {

}
