package ru.practicum.comment.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.mapper.CommentMapper;
import ru.practicum.comment.model.Comment;
import ru.practicum.comment.repository.CommentRepository;
import ru.practicum.comment.service.CommentService;
import ru.practicum.common.exception.ConflictException;
import ru.practicum.common.exception.NotFoundException;
import ru.practicum.common.util.Constants;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventState;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentMapper commentMapper;

    @Override
    public Collection<CommentDto> getAllComments(int from, int size) {
        PageRequest page = Constants.getDefaultPageRequest(from, size);
        Collection<CommentDto> commentDtos = commentMapper.convertToDtoCollection(
                commentRepository.findAll(page).getContent());
        log.info("Запрос комментарий - {}", commentDtos);
        return commentDtos;
    }

    @Override
    public Collection<CommentDto> getAllComments(long userId, Long eventId, int from, int size) {
        PageRequest page = Constants.getDefaultPageRequest(from, size);
        Collection<Comment> comments = eventId == null ?
                commentRepository.findAllByAuthorId(userId, page).getContent() :
                commentRepository.findAllByAuthorIdAndEventId(userId, eventId, page).getContent();
        Collection<CommentDto> commentDtos = commentMapper.convertToDtoCollection(comments);
        log.info("Запрос комментариев пользователя по id = {} события по id = {} - {}", userId, eventId, commentDtos);
        return commentDtos;
    }

    @Override
    public Collection<CommentDto> getAllComments(long eventId, int from, int size) {
        PageRequest page = Constants.getDefaultPageRequest(from, size);
        Collection<Comment> comments = commentRepository.findAllByEventId(eventId, page).getContent();
        Collection<CommentDto> commentDtos = commentMapper.convertToDtoCollection(comments);
        log.info("Запрос комментариев события по id = {} - {}", eventId, commentDtos);
        return commentDtos;
    }

    @Override
    public CommentDto getCommentById(long commentId) {
        Comment comment = getCommentByIdOrElseThrow(commentId);
        CommentDto commentDto = commentMapper.convertToDto(comment);
        log.info("Запрошен комментарий по id = {} - {}", commentId, commentDto);
        return commentDto;
    }

    @Override
    @Transactional
    public CommentDto addNewComment(long userId, long eventId, NewCommentDto newCommentDto) {
        User user = getUserByIdOrElseThrow(userId);
        Event event = getEventByIdOrElseThrow(eventId);

        if (event.getState() != EventState.PUBLISHED) {
            log.info("Нельзя добавить комментарий для неопубликованного события");
            throw new ConflictException("Нельзя добавить комментарий для неопубликованного события");
        }

        Comment comment = commentMapper.convertNewCommentDtoToEntity(newCommentDto);
        comment.setAuthor(user);
        comment.setEvent(event);
        CommentDto commentDto = commentMapper.convertToDto(commentRepository.save(comment));
        log.info("Добавлен новый комментарий - {}", commentDto);
        return commentDto;
    }

    @Override
    @Transactional
    public void deleteComment(long commentId) {
        checkCommentIfExists(commentId);
        commentRepository.deleteById(commentId);
        log.info("Комментарий по id = {} удален", commentId);
    }

    @Override
    @Transactional
    public void deleteComment(long userId, long commentId) {
        User user = getUserByIdOrElseThrow(userId);
        Comment comment = getCommentByIdOrElseThrow(commentId);
        checkUserIsCommentAuthor(comment, user);
        throwIfChangeTimeHasExpired(comment);
        commentRepository.deleteById(commentId);
        log.info("Комментарий по id = {} пользователя по id = {} удален", commentId, user.getId());
    }

    @Override
    @Transactional
    public CommentDto editComment(long userId, long commentId, NewCommentDto newCommentDto) {
        User user = getUserByIdOrElseThrow(userId);
        Comment comment = getCommentByIdOrElseThrow(commentId);
        checkUserIsCommentAuthor(comment, user);
        throwIfChangeTimeHasExpired(comment);
        Comment updatingComment = commentMapper.clone(comment);
        commentMapper.updateCommentFromNewCommentDto(newCommentDto, updatingComment);
        log.info("Комментарий подготовлен к обновлению - {}", updatingComment);
        return commentMapper.convertToDto(commentRepository.save(updatingComment));
    }

    private void throwIfChangeTimeHasExpired(Comment comment) {
        LocalDateTime currentTime = LocalDateTime.now();
        if (!currentTime.isBefore(comment.getCreatedOn().plusHours(1))) {
            log.info("Изменить/удалить комментарий возможно за 1 час с момента создания комментария");
            throw new ConflictException(
                    "Изменить/удалить комментарий возможно за 1 час с момента создания комментария");
        }
    }

    private void checkCommentIfExists(long commentId) {
        if (!commentRepository.existsById(commentId)) {
            log.info("Комментария по id = {} не существует", commentId);
            throw new NotFoundException(String.format("Комментария по id = %d не существует", commentId));
        }
    }

    private void checkUserIsCommentAuthor(Comment comment, User user) {
        Long commentId = comment.getAuthor().getId();
        if (!commentId.equals(user.getId())) {
            log.info("Пользователя по id = {} не является автором комментария по id = {}", user.getId(), commentId);
            throw new ConflictException(
                    String.format("Пользователя по id = %d не является автором комментария по id = %d",
                            user.getId(), commentId));
        }
    }

    private User getUserByIdOrElseThrow(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.info("Пользователя по id = {} не существует", userId);
                    return new NotFoundException(String.format("Пользователя по id = %d не существует", userId));
                });
    }

    private Event getEventByIdOrElseThrow(long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> {
                    log.info("События по id = {} не существует", eventId);
                    return new NotFoundException(String.format("События по id = %d не существует", eventId));
                });
    }

    private Comment getCommentByIdOrElseThrow(long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> {
                    log.info("Комментария по id = {} не существует", commentId);
                    return new NotFoundException(String.format("Комментария по id = %d не существует", commentId));
                });
    }
}
