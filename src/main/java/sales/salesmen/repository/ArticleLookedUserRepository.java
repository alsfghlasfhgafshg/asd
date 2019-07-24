package sales.salesmen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sales.salesmen.entity.Article;
import sales.salesmen.entity.ArticleLookedUser;

public interface ArticleLookedUserRepository extends JpaRepository<ArticleLookedUser, Long> {


}
