package sales.salesmen.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sales.salesmen.esentity.EsCourse;
import sales.salesmen.repository.es.EsCourseRepository;
import sales.salesmen.service.EsCourseService;

@Service
public class EsCourseServiceImpl implements EsCourseService {

    @Autowired
    private EsCourseRepository esCourseRepository;

    @Override
    public void removeEsCourse(String id) {
        esCourseRepository.deleteById(id);
    }

    @Override
    public EsCourse updateEsCourse(EsCourse esCourse) {
        return esCourseRepository.save(esCourse);
    }

    @Override
    public EsCourse getEsCourseByCourseId(Long courseId) {
        return esCourseRepository.findByCourseId(courseId);
    }

    @Override
    public Page<EsCourse> listEsCourse(Pageable pageable) {
        return esCourseRepository.findAll(pageable);
    }

    @Override
    public Page<EsCourse> listNewestEsCourse(String keyword, Pageable pageable) {
        Page<EsCourse> pages = null;
        Sort sort = new Sort(Sort.Direction.DESC,"courseId");
        if (pageable.getSort().isUnsorted()){
            pageable = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(),sort);
        }
        pages = esCourseRepository.findByTitleContainingOrTeacherContainingOrSummaryContaining(keyword,keyword,keyword,pageable);
        return pages;
    }
}
