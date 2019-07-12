package sales.salesmen.service;

import sales.salesmen.entity.ACatalog;

import java.util.Optional;

public interface ACatalogService {
    Optional<ACatalog> findCatalogById(Integer id);
}
