package ru.practicum.event.service;

import ru.practicum.event.dto.*;
import ru.practicum.request.dto.ParticipationRequestDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collection;

public interface EventService {
    public Collection<EventFullDto> getAllEvents(Collection<Long> users, Collection<String> states,
                                                 Collection<Long> categories, LocalDateTime rangeStart,
                                                 LocalDateTime rangeEnd, int from, int size);

    public Collection<EventShortDto> getAllEvents(String text, Collection<Long> categories, Boolean paid,
                                                  LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                  boolean onlyAvailable, String sort, int from, int size,
                                                  HttpServletRequest request);

    public Collection<EventShortDto> getAllEvents(long userId, int from, int size);

    public Collection<ParticipationRequestDto> getEventRequestsById(long userId, long eventId);

    public EventFullDto getEventById(long id, HttpServletRequest request);

    public EventFullDto getEventById(long userId, long eventId);

    public EventFullDto addNewEvent(long userId, NewEventDto newEventDto);

    public EventFullDto editEvent(long userId, long eventId, UpdateEventUserRequest updateEventUserRequest);

    public EventFullDto editEvent(long eventId, UpdateEventAdminRequest updateEventAdminRequest);

    public EventRequestStatusUpdateResult editEventRequest(long userId, long eventId,
                                                           EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest);
}
