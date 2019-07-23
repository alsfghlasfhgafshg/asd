package sales.salesmen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sales.salesmen.entity.Carousel;

import java.util.List;


public interface CarouselRepository extends JpaRepository<Carousel, Integer> {

    List<Carousel> findAllByCourseNotNull();

    List<Carousel> findAllByArticleNotNull();

}
