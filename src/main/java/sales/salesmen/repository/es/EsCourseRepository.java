package sales.salesmen.repository.es;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import sales.salesmen.esentity.EsCourse;

public interface EsCourseRepository extends ElasticsearchRepository<EsCourse,String> {
    Page<EsCourse> findByTitleContainingOrTeacherContainingOrSummaryContaining(
            String title, String teacher, String summary, Pageable pageable);
    EsCourse findByCourseId(Long courseId);
}
