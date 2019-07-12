package sales.salesmen.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sales.salesmen.entity.Authority;
import sales.salesmen.repository.AuthorityRepository;
import sales.salesmen.service.AuthorityService;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public Optional<Authority> getAuthorityById(Long id) {
        return authorityRepository.findById(id);
    }

    @Override
    public List<Authority> findAll() {
        return authorityRepository.findAll();
    }
}
