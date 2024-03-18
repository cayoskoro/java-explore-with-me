package ru.practicum.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.service.CommentService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

@RestController
@RequestMapping(path = "/comments")
@RequiredArgsConstructor
@Validated
public class CommentPublicController {
    private final CommentService commentService;

    @GetMapping
    public Collection<CommentDto> getAllComments(@RequestParam long eventId,
                                                 @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                 @RequestParam(defaultValue = "10") @Positive int size) {
        return commentService.getAllComments(eventId, from, size);
    }

    @GetMapping("/{commentId}")
    public CommentDto getCommentById(@PathVariable long commentId) {
        return commentService.getCommentById(commentId);
    }

}
