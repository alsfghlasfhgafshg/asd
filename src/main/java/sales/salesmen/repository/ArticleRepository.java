package sales.salesmen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sales.salesmen.entity.Article;

public interface ArticleRepository extends JpaRepository<Article,Long> {

}
