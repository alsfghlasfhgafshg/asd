package sales.salesmen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sales.salesmen.entity.User;
import sales.salesmen.entity.WxUser;

public interface WxUserRepository extends JpaRepository<WxUser, String> {
}
