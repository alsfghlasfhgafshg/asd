package sales.salesmen.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sales.salesmen.esentity.EsCourse;

public interface EsCourseService {
    void removeEsCourse(String id);
    EsCourse updateEsCourse(EsCourse esProduct);
    EsCourse getEsCourseByCourseId(Long courseId);
    Page<EsCourse> listEsCourse(Pageable pageable);
    Page<EsCourse> listNewestEsCourse(String keyword,Pageable pageable);
}
