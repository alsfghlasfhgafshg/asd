package sales.salesmen.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sales.salesmen.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
