package ru.practicum.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Collection;

@Value
@Builder(toBuilder = true)
public class EventRequestStatusUpdateResult {
    private final Collection<ParticipationRequestDto> confirmedRequests;
    private final Collection<ParticipationRequestDto> rejectedRequests;
}
