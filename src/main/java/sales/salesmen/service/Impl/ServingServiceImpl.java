package sales.salesmen.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sales.salesmen.entity.SCatalog2;
import sales.salesmen.entity.Serving;
import sales.salesmen.repository.ServingRepository;
import sales.salesmen.service.FileService;
import sales.salesmen.service.SCatalogService;
import sales.salesmen.service.ServingService;

@Service
public class ServingServiceImpl implements ServingService {
    @Autowired
    FileService fileService;

    @Autowired
    ServingRepository servingRepository;

    @Autowired
    SCatalogService sCatalogService;

    @Override
    public Serving saveServing(Serving serving) {
        return servingRepository.save(serving);
    }

    @Override
    public Serving saveServing(int catalog2id, String title, String subtitle, MultipartFile file, String summary, String price) {

        Serving serving = new Serving();
        SCatalog2 sCatalog2 = sCatalogService.getSCatalog2(catalog2id);
        serving.setsCatalog2(sCatalog2);
        serving.setSummary(summary);
        serving.setPrice("价格 ：" + price);
        String pic = fileService.uploadImage(file);
        serving.setTitle(title);
        serving.setSubtitle(subtitle);
        serving.setPictureuri(pic);

        return saveServing(serving);
    }

    @Override
    public boolean removeServing(Serving serving) {
        try {
            servingRepository.delete(serving);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Serving getServingById(Long id) {
        return servingRepository.findById(id).get() ;
    }

    @Override
    public Page<Serving> getServingByPage(Pageable pageable) {
        return servingRepository.findAll(pageable);
    }


}
