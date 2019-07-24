package sales.salesmen.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sales.salesmen.entity.User;

import java.util.Collection;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    Page<User> findByUsernameLike(String username, Pageable pageable);

    List<User> findAllByIdIn(List<Long> id);
}
