package sales.salesmen.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import sales.salesmen.entity.Article;
import sales.salesmen.entity.ArticleLookedUser;

import java.util.List;

public interface ArticleLookedUserRepository extends JpaRepository<ArticleLookedUser, Long> {
    List<ArticleLookedUser> findAllBySharedUserId(@Qualifier("shared_user_id") Long sharedUserId);
}
