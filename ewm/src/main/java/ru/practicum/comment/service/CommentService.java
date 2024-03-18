package ru.practicum.comment.service;

import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;

import java.util.Collection;

public interface CommentService {
    public Collection<CommentDto> getAllComments(int from, int size);

    public Collection<CommentDto> getAllComments(long userId, Long eventId, int from, int size);

    public Collection<CommentDto> getAllComments(long eventId, int from, int size);

    public CommentDto getCommentById(long commentId);

    public CommentDto addNewComment(long userId, long eventId, NewCommentDto newCommentDto);

    public void deleteComment(long commentId);

    public void deleteComment(long userId, long commentId);

    public CommentDto editComment(long userId, long commentId, NewCommentDto newCommentDto);
}
