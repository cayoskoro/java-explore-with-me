package ru.practicum.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;
import ru.practicum.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
public class CommentDto {
    private final Long id;
    private final String text;
    private final Long eventId;
    private final UserShortDto author;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdOn;
}
