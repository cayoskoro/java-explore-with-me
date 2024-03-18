package ru.practicum.comment.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@Builder(toBuilder = true)
public class NewCommentDto {
    @NotBlank
    @Size(min = 3, max = 4096)
    private final String text;
}
