package sales.salesmen.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sales.salesmen.entity.SCatalog;
import sales.salesmen.entity.SCatalog2;
import sales.salesmen.repository.SCatalog2Repository;
import sales.salesmen.repository.SCatalogRepository;
import sales.salesmen.service.SCatalogService;

import java.util.List;

@Service
public class ScatalogServiceImpl implements SCatalogService {

    @Autowired
    SCatalogRepository sCatalogRepository;

    @Autowired
    SCatalog2Repository sCatalog2Repository;


    @Override
    public SCatalog getSCatalogById(int scatalogid) {
        return sCatalogRepository.findById(scatalogid).get();
    }

    @Override
    public List<SCatalog> getAllSCatalog() {
        return sCatalogRepository.findAll();
    }

    @Override
    public List<SCatalog2> getAllSCatalog2(SCatalog scatalog) {
        return sCatalog2Repository.findBySCatalog(scatalog);
    }

    @Override
    public SCatalog addSCatalog(SCatalog sCatalog) {
        return sCatalogRepository.save(sCatalog);
    }

    @Override
    public SCatalog2 addSCatalog2(SCatalog2 sCatalog2) {
        return sCatalog2Repository.save(sCatalog2);
    }

    @Override
    public SCatalog2 getSCatalog2(int sCatalog2id) {
       return sCatalog2Repository.findById(sCatalog2id).get();
    }
}
