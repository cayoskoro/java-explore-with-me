package ru.practicum.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
public class NewEventDto {
    private final String title;
    private final String annotation;
    private final Long category;
    private final boolean paid;
    private final LocalDateTime eventDate;
    private final String description;
    private final Integer participantLimit;
    private final boolean requestModeration;
    private final Location location;
}
