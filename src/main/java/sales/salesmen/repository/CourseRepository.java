package sales.salesmen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sales.salesmen.entity.Course;

public interface CourseRepository extends JpaRepository<Course,Long> {
}
