package sales.salesmen.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sales.salesmen.entity.Comment;
import sales.salesmen.repository.CommentRepository;
import sales.salesmen.service.CommentService;

import java.util.Optional;

public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Optional<Comment> findCommentById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void removeCommentById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Page<Comment> listAllCommentByArticleId(Long id, Pageable pageable) {
        return commentRepository.findAllById(id,pageable);
    }

}
