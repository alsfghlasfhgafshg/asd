package sales.salesmen.service;

import sales.salesmen.entity.SCatalog;
import sales.salesmen.entity.SCatalog2;

import java.util.List;

public interface SCatalogService {

    SCatalog getSCatalogById(int scatalogid);

    List<SCatalog> getAllSCatalog();

    List<SCatalog2> getAllSCatalog2(SCatalog scatalog);

    SCatalog addSCatalog(SCatalog sCatalog);

    SCatalog2 addSCatalog2(SCatalog2 sCatalog2);

    SCatalog2 getSCatalog2(int sCatalog2id);

}
