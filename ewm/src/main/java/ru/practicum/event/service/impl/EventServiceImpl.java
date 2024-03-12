package ru.practicum.event.service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.model.Category;
import ru.practicum.event.dto.*;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventState;
import ru.practicum.common.exception.NotFoundException;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.QEvent;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.mapper.RequestMapper;
import ru.practicum.request.model.Request;
import ru.practicum.request.model.RequestStatus;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.repository.UserRepository;
import ru.practicum.event.service.EventService;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {
    private final EventMapper eventMapper;
    private final RequestMapper requestMapper;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;

    @Override
    public Collection<EventFullDto> getAllEvents(Collection<Long> users, Collection<String> states,
                                                 Collection<Long> categories,
                                                 LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size) {
        QEvent qEvent = QEvent.event;
        Collection<BooleanExpression> conditions = new ArrayList<>();
        if (users != null && !users.isEmpty()) {
            conditions.add(qEvent.initiator.id.in(users));
        }
        if (states != null && !states.isEmpty()) {
            conditions.add(qEvent.state.stringValue().in(states));
        }
        if (categories != null && !categories.isEmpty()) {
            conditions.add(qEvent.category.id.in(categories));
        }
        if (rangeStart != null) {
            conditions.add(qEvent.eventDate.after(rangeStart));
        }
        if (rangeEnd != null) {
            conditions.add(qEvent.eventDate.before(rangeEnd));
        }
        BooleanExpression commonCondition = conditions.stream()
                .reduce(BooleanExpression::and)
                .get();

        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        Collection<EventFullDto> eventDtos = eventMapper.convertToFullDtoCollection(
                eventRepository.findAll(commonCondition, page).getContent());
        log.info("Запрос списка событий по commonCondition = {}, users = {}, states = {}, categories = {}, " +
                "rangeStart = {}, rangeEnd = {} " + "- {}", commonCondition, users, states, categories,
                rangeStart, rangeEnd, eventDtos);
        return eventDtos;
    }

    @Override
    public Collection<EventShortDto> getAllEvents(String text, Collection<Long> categories, boolean paid,
                                                  LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                  boolean onlyAvailable, String sort, int from, int size) {
        LocalDateTime currentTime = LocalDateTime.now();
        QEvent qEvent = QEvent.event;
        Collection<BooleanExpression> conditions = new ArrayList<>();
        conditions.add(qEvent.state.eq(EventState.PUBLISHED));
        if (text != null) {
            conditions.add(qEvent.annotation.likeIgnoreCase(text).or(qEvent.description.likeIgnoreCase(text)));
        }
        if (categories != null && !categories.isEmpty()) {
            conditions.add(qEvent.category.id.in(categories));
        }
        conditions.add(qEvent.paid.eq(paid));
        conditions.add(qEvent.eventDate.after(rangeStart != null ? rangeStart : currentTime));
        if (rangeEnd != null) {
            conditions.add(qEvent.eventDate.before(rangeEnd));
        }
//        conditions.add(qEvent.onlyAvailable.eq(onlyAvailable));
        BooleanExpression commonCondition = conditions.stream()
                .reduce(BooleanExpression::and)
                .get();

        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        Collection<EventShortDto> eventDtos = eventMapper.convertToShortDtoCollection(
                eventRepository.findAll(commonCondition, page).getContent());
//        makeSortCondition(sort);
        log.info("Запрос списка событий по text = {}, categories = {}, paid = {}, rangeStart = {}, rangeEnd = {} " +
                "onlyAvailable = {}, sort = {} - {}", text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort,
                eventDtos);
        return eventDtos;
    }

    @Override
    public Collection<EventShortDto> getAllEvents(long userId, int from, int size) {
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        checkUserIfExists(userId);
        Collection<EventShortDto> eventDtos = eventMapper.convertToShortDtoCollection(eventRepository
                .findAllByInitiatorId(userId, page).getContent());
        log.info("Запрос списка событий пользователя по id = {} - {}", userId, eventDtos);
        return eventDtos;
    }

    @Override
    public Collection<ParticipationRequestDto> getEventRequestsById(long userId, long eventId) {
        return null;
    }

    @Override
    public EventFullDto getEventById(long id) {
        Event event = getEventByIdOrElseThrow(id);
        if (!event.getState().equals(EventState.PUBLISHED)) {
            log.info("Событие не является опубликованным");
            throw new NotFoundException("Событие не является опубликованным");
        }
        EventFullDto eventDto = eventMapper.convertToFullDto(event);
        log.info("Запрошено событие по id = {} - {}", id, eventDto);
        return eventDto;
    }

    @Override
    public EventFullDto getEventById(long userId, long eventId) {
        checkUserIfExists(userId);
        Event event = getEventByIdOrElseThrow(eventId);
        EventFullDto eventDto = eventMapper.convertToFullDto(event);
        log.info("Запрошено для пользователя по id = {} событие по id = {} - {}", userId, eventId, eventDto);
        return eventDto;
    }

    @Override
    @Transactional
    public EventFullDto addNewEvent(long userId, NewEventDto newEventDto) {
        LocalDateTime currentTime = LocalDateTime.now();
        if (!currentTime.plusHours(2).isBefore(newEventDto.getEventDate())) {
            log.info("Дата события должна быть запланирована за два часа до настоящего момента" +
                    " currentTime = {}, eventDate = {}", currentTime, newEventDto.getEventDate());
            throw new IllegalArgumentException("Дата события должна быть запланирована за два часа до настоящего момента");
        }

        User user = getUserByIdOrElseThrow(userId);
        Category category = getCategoryByIdOrElseThrow(newEventDto.getCategory());
        Event event = eventMapper.convertNewEventDtoToEntity(newEventDto);
        event.setCreatedOn(currentTime);
        event.setInitiator(user);
        event.setCategory(category);
        event.setConfirmedRequests(0);
        log.info("event = {}", event);
        EventFullDto eventDto = eventMapper.convertToFullDto(eventRepository.save(event));
        log.info("Добавлено новое событие - {}", eventDto);
        return eventDto;
    }

    @Override
    @Transactional
    public EventFullDto editEvent(long userId, long eventId, UpdateEventUserRequest updateEventUserRequest) {
        checkUserIfExists(userId);
        Event event = getEventByIdOrElseThrow(eventId);

        if (!event.getState().equals(EventState.CANCELED) && !event.isRequestModeration()) {
            log.info("Событие не соответствует правилам для изменения: " +
                    "Статус отмененный <> {} или в состоянии ожидания модерации <> {}", event.getState(),
                    event.isRequestModeration());
            throw new IllegalStateException("Only pending or canceled events can be changed");
        }

        LocalDateTime currentTime = LocalDateTime.now();
        if (updateEventUserRequest.getEventDate() != null && !currentTime.plusHours(2).isBefore(
                updateEventUserRequest.getEventDate())) {
            log.info("Дата события должна быть запланирована за два часа до настоящего момента");
            throw new IllegalArgumentException();
        }

        Event updatingEvent = eventMapper.clone(event);
        eventMapper.updateEventFromEventUserRequest(updateEventUserRequest, updatingEvent);
        log.info("Событие подготовлено к обновлению - {}", updatingEvent);
        return eventMapper.convertToFullDto(eventRepository.save(updatingEvent));
    }

    @Override
    @Transactional
    public EventFullDto editEvent(long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        Event event = getEventByIdOrElseThrow(eventId);

        LocalDateTime currentTime = LocalDateTime.now();
        if (updateEventAdminRequest.getEventDate() != null && !currentTime.plusHours(1).isBefore(
                updateEventAdminRequest.getEventDate())) {
            log.info("Дата события должна быть запланирована за час до настоящего момента");
            throw new IllegalArgumentException();
        }

        UpdateEventAdminRequest.StateAction stateAction = updateEventAdminRequest.getStateAction();
        if (stateAction.equals(UpdateEventAdminRequest.StateAction.PUBLISH_EVENT)
                && !event.getState().equals(EventState.PENDING)
                || stateAction.equals(UpdateEventAdminRequest.StateAction.REJECT_EVENT)
                && !event.getState().equals(EventState.PUBLISHED)) {
            log.info("Событие не соответствует правилам для изменения: stateAction = {}, eventState = {}",
                    stateAction, event.getState());
            throw new IllegalStateException("Cannot publish or reject the event because it's not in the right state");
        }
        switch (stateAction) {
            case PUBLISH_EVENT:
                event.setState(EventState.PUBLISHED);
                event.setPublishedOn(currentTime);
                break;
            case REJECT_EVENT:
                event.setState(EventState.CANCELED);
                break;
        }

        Event updatingEvent = eventMapper.clone(event);
        eventMapper.updateEventFromEventAdminRequest(updateEventAdminRequest, updatingEvent);
        log.info("Событие подготовлено к обновлению - {}", updatingEvent);
        return eventMapper.convertToFullDto(eventRepository.save(updatingEvent));
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult editEventRequest(long userId, long eventId,
                                                           EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        User user = getUserByIdOrElseThrow(userId);
        Event event = getEventByIdOrElseThrow(eventId);
        checkEventInitiator(user, event);

        Collection<Request> updatingRequests = requestRepository.findAllByIdInAndStatus(
                eventRequestStatusUpdateRequest.getRequestIds(), RequestStatus.PENDING);

        Collection<Long> noPendingRequests = new ArrayList<>(eventRequestStatusUpdateRequest.getRequestIds());
        noPendingRequests.removeAll(updatingRequests.stream().map(Request::getId).collect(Collectors.toList()));
        if (!noPendingRequests.isEmpty()) {
            log.info("Присутствуют запросы не в статусе PENDING - {}", noPendingRequests);
            throw new IllegalStateException("Присутствуют запросы не в статусе PENDING - " + noPendingRequests);
        }

        Collection<Request> confirmedRequests = new ArrayList<>();
        Collection<Request> rejectedRequests = new ArrayList<>();
        Integer countConfirmedRequests = event.getConfirmedRequests();
        switch (eventRequestStatusUpdateRequest.getStatus()) {
            case CONFIRMED:
                if (event.getParticipantLimit() != 0 && countConfirmedRequests >= event.getParticipantLimit()) {
                    log.info("Лимит заявок исчерпан participantLimit =  {}", event.getParticipantLimit());
                    throw new IllegalArgumentException("Лимит заявок исчерпан participantLimit = " +
                            event.getParticipantLimit());
                }

                for (Request it : updatingRequests) {
                    if (countConfirmedRequests < event.getParticipantLimit()) {
                        it.setStatus(RequestStatus.CONFIRMED);
                        confirmedRequests.add(it);
                        countConfirmedRequests++;
                    } else {
                        it.setStatus(RequestStatus.REJECTED);
                        rejectedRequests.add(it);
                    }
                }
                break;
            case REJECTED:
                updatingRequests.forEach(it -> {
                    it.setStatus(RequestStatus.REJECTED);
                    rejectedRequests.add(it);
                });
                break;
        }

        event.setConfirmedRequests(countConfirmedRequests);
        log.info("Событие - {} подготовлено к обновлению после изменение статусов запросов", event);
        eventRepository.save(event);
        log.info("Событие подготовлены к обновлению после изменение статусов - {}", updatingRequests);
        requestRepository.saveAll(updatingRequests);
        log.info("Запросы пользователя по id = {} и событию по id - {} обновлены - {}", userId, eventId,
                updatingRequests);
        return EventRequestStatusUpdateResult.builder()
                .confirmedRequests(requestMapper.convertToParticipationRequestDtoCollection(confirmedRequests))
                .rejectedRequests(requestMapper.convertToParticipationRequestDtoCollection(rejectedRequests))
                .build();
    }

    private enum EventSort {
        EVENT_DATE, VIEWS
    }

    private Sort makeSortCondition(EventSort eventSort) {
        switch (eventSort) {
            case EVENT_DATE:
                return Sort.by("eventDate").ascending();
            case VIEWS:
                return Sort.by("views").ascending();
            default:
                return Sort.by("eventDate").descending();
        }
    }

    private Event getEventByIdOrElseThrow(long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> {
                    log.info("События по id = {} не существует", eventId);
                    return new NotFoundException(String.format("События по id = %d не существует", eventId));
                });
    }

    private void checkUserIfExists(long userId) {
        if (!userRepository.existsById(userId)) {
            log.info("Пользователя по id = {} не существует", userId);
            throw new NotFoundException(String.format("Пользователя по id = %d не существует", userId));
        }
    }

    private void checkEventInitiator(User user, Event event) {
        if (!event.getInitiator().getId().equals(user.getId())) {
            log.info("Пользователь по id = {} не является инициатором события по id = {}",
                    user.getId(), event.getId());
            throw new IllegalStateException(String.format("Пользователь по id = %d не является инициатором " +
                    "события по id = %d", user.getId(), event.getId()));
        }
    }

    private User getUserByIdOrElseThrow(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.info("Пользователя по id = {} не существует", userId);
                    return new NotFoundException(String.format("Пользователя по id = %d не существует", userId));
                });
    }

    private Category getCategoryByIdOrElseThrow(long catId) {
        return categoryRepository.findById(catId)
                .orElseThrow(() -> {
                    log.info("Категории по id = {} не существует", catId);
                    return new NotFoundException(String.format("Категории по id = %d не существует", catId));
                });
    }
}
