package sales.salesmen.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sales.salesmen.entity.PCatalog;
import sales.salesmen.repository.PCatalogRepository;
import sales.salesmen.service.PCatalogService;

import java.util.Optional;

@Service
public class PCatalogServiceImpl implements PCatalogService {

    @Autowired
    private PCatalogRepository pCatalogRepository;

    @Override
    public Optional<PCatalog> findCatalogById(Integer id) {
        return pCatalogRepository.findById(id);
    }
}
