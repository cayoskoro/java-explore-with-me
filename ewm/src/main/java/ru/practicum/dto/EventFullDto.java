package ru.practicum.dto;

import lombok.Builder;
import lombok.Value;
import ru.practicum.model.EventState;

import java.time.LocalDateTime;
import java.util.Collection;

@Value
@Builder(toBuilder = true)
public class EventFullDto {
    private final Long id;
    private final String title;
    private final String annotation;
    private final CategoryDto category;
    private final boolean paid;
    private final LocalDateTime eventDate;
    private final UserShortDto initiator;
    private final Long confirmedRequests;
    private final Long views;
    private final String description;
    private final Integer participantLimit;
    private final EventState state;
    private final LocalDateTime createdOn;
    private final boolean requestModeration;
    private final Location location;
    private final LocalDateTime publishedOn;
}
