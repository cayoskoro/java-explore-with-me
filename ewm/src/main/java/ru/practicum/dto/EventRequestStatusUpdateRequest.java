package ru.practicum.dto;

import lombok.Builder;
import lombok.Value;
import ru.practicum.model.RequestStatus;

import java.util.Collection;

@Value
@Builder(toBuilder = true)
public class EventRequestStatusUpdateRequest {
    private final Collection<Long> requestIds;
    private final RequestStatus status;
}