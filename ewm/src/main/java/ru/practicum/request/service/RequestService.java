package ru.practicum.request.service;

import ru.practicum.request.dto.ParticipationRequestDto;

import java.util.Collection;

public interface RequestService {
    public Collection<ParticipationRequestDto> getAllRequests(long userId);

    public ParticipationRequestDto addNewRequest(long userId, long eventId);

    public ParticipationRequestDto cancelRequest(long userId, long requestId);
}
