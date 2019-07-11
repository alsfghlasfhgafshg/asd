package sales.salesmen.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sales.salesmen.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    User SaveOrUpdateUser(User user);
    User registerUser(User user);
    void removeUser(Long id);
    Optional<User> getUserById(Long id);
    Page<User> listUserByUsernameLike(String username,Pageable pageable);
}
