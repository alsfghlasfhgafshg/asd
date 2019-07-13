package sales.salesmen.service;

import sales.salesmen.entity.PCatalog;

import java.util.Optional;

public interface PCatalogService {
    Optional<PCatalog> findCatalogById(Integer id);
}
