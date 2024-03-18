package ru.practicum.comment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.comment.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    public Page<Comment> findAllByAuthorIdAndEventId(long userId, long eventId, Pageable page);

    public Page<Comment> findAllByEventId(long eventId, Pageable page);

    public Page<Comment> findAllByAuthorId(long userId, Pageable page);
}
