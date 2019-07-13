package sales.salesmen.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sales.salesmen.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<Comment> findCommentById(Long id);
    Comment saveComment(Comment comment);
    void removeCommentById(Long id);
    Page<Comment> listAllCommentByArticleId(Long id, Pageable pageable);
}
