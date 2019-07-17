package sales.salesmen.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sales.salesmen.entity.CCatalog;
import sales.salesmen.entity.CCatalog2;
import sales.salesmen.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Page<Course> findAllByCCatalogAndAndCCatalog2(Pageable pageable, CCatalog catalog, CCatalog2 cCatalog2);

}
