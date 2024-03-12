package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
public class EventShortDto {
    private final Long id;
    private final String title;
    private final String annotation;
    private final CategoryDto category;
    private final boolean paid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime eventDate;
    private final UserShortDto initiator;
    private final Integer confirmedRequests;
    private final Long views;
}
