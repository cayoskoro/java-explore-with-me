package ru.practicum.request.dto;

import lombok.Builder;
import lombok.Value;
import ru.practicum.request.model.RequestStatus;

import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
public class ParticipationRequestDto {
    private final Long id;
    private final Long event;
    private final Long requester;
    private final RequestStatus status;
    private final LocalDateTime created;
}
