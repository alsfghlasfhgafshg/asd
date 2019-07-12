package sales.salesmen.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sales.salesmen.entity.ACatalog;
import sales.salesmen.repository.ACatalogRepository;
import sales.salesmen.service.ACatalogService;

import java.util.Optional;

@Service
public class ACatalogServiceImpl implements ACatalogService {

    @Autowired
    private ACatalogRepository aCatalogRepository;

    @Override
    public Optional<ACatalog> findCatalogById(Integer id) {
        return aCatalogRepository.findById(id);
    }
}
