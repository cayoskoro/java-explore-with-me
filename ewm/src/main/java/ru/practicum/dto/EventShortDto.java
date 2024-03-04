package ru.practicum.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
public class EventShortDto {
    private final Long id;
    private final String title;
    private final String annotation;
    private final CategoryDto category;
    private final boolean paid;
    private final LocalDateTime eventDate;
    private final UserShortDto initiator;
    private final Long confirmedRequests;
    private final Long views;
}
