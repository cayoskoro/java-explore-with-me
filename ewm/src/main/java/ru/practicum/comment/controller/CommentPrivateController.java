package ru.practicum.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.service.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

@RestController
@RequestMapping(path = "/users/{userId}/comments")
@RequiredArgsConstructor
@Validated
public class CommentPrivateController {
    private final CommentService commentService;

    @GetMapping
    public Collection<CommentDto> getAllComments(@PathVariable long userId,
                                                 @RequestParam(required = false) Long eventId,
                                                 @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                 @RequestParam(defaultValue = "10") @Positive int size) {
        return commentService.getAllComments(userId, eventId, from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto addNewComment(@PathVariable long userId,
                                    @RequestParam long eventId,
                                    @RequestBody @Valid NewCommentDto newCommentDto) {
        return commentService.addNewComment(userId, eventId, newCommentDto);
    }

    @PatchMapping("/{commentId}")
    public CommentDto editComment(@PathVariable long userId,
                                  @PathVariable long commentId,
                                  @RequestBody @Valid NewCommentDto newCommentDto) {
        return commentService.editComment(userId, commentId, newCommentDto);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable long userId, @PathVariable long commentId) {
        commentService.deleteComment(userId, commentId);
    }
}
