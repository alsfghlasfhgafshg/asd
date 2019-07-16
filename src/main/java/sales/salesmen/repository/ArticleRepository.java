package sales.salesmen.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sales.salesmen.entity.ACatalog;
import sales.salesmen.entity.Article;

public interface ArticleRepository extends JpaRepository<Article,Long> {

    Page<Article> findAllByACatalog(ACatalog aCatalog, Pageable pageable);

}
