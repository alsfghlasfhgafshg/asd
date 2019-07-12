package sales.salesmen.service;

import sales.salesmen.entity.Authority;

import java.util.List;
import java.util.Optional;

public interface AuthorityService {
    Optional<Authority> getAuthorityById(Long id);
    List<Authority> findAll();
}
