package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.*;
import ru.practicum.service.EventService;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {
    @Override
    public Collection<EventFullDto> getAllEvents(Collection<Long> users, Collection<String> states, Collection<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size) {
        return null;
    }

    @Override
    public Collection<EventShortDto> getAllEvents(String text, Collection<Long> categories, boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, boolean onlyAvailable, String sort, int from, int size) {
        return null;
    }

    @Override
    public Collection<EventShortDto> getAllEvents(long userId, int from, int size) {
        return null;
    }

    @Override
    public Collection<ParticipationRequestDto> getEventRequestsById(long userId, long eventId) {
        return null;
    }

    @Override
    public EventFullDto getEventById(long id) {
        return null;
    }

    @Override
    public EventFullDto getEventById(long userId, long eventId) {
        return null;
    }

    @Override
    @Transactional
    public EventFullDto addNewEvent(long userId, NewEventDto newEventDto) {
        return null;
    }

    @Override
    @Transactional
    public EventFullDto editEvent(long userId, long eventId, UpdateEventUserRequest updateEventUserRequest) {
        return null;
    }

    @Override
    @Transactional
    public EventFullDto editEvent(long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        return null;
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult editEventRequest(long userId, long eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        return null;
    }
}
