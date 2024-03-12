package ru.practicum.request.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.request.dto.ParticipationRequestDto;

import java.util.Collection;

@Transactional(readOnly = true)
public interface RequestService {
    public Collection<ParticipationRequestDto> getAllRequests(long userId);

    @Transactional
    public ParticipationRequestDto addNewRequest(long userId, long eventId);

    @Transactional
    public ParticipationRequestDto cancelRequest(long userId, long requestId);
}
