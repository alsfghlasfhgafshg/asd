package sales.salesmen.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import sales.salesmen.entity.Serving;

import java.util.Optional;

public interface ServingService {
    Serving saveServing(Serving serving);

    Serving saveServing(int catalog2id, String title,
                        String subtitle, MultipartFile file,
                        String summary, String price);

    boolean removeServing(Serving serving);

    Serving getServingById(Long id);

    Page<Serving> getServingByPage(Pageable pageable);

}
