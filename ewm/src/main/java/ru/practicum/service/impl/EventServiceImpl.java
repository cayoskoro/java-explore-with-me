package ru.practicum.service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.*;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.CategoryMapper;
import ru.practicum.mapper.EventMapper;
import ru.practicum.mapper.UserMapper;
import ru.practicum.model.*;
import ru.practicum.repository.CategoryRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.UserRepository;
import ru.practicum.service.EventService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {
    private final EventMapper eventMapper;
    private final UserMapper userMapper;
    private final CategoryMapper categoryMapper;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Collection<EventFullDto> getAllEvents(Collection<Long> users, Collection<String> states,
                                                 Collection<Long> categories,
                                                 LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size) {
        QEvent qEvent = QEvent.event;
        Collection<BooleanExpression> conditions = new ArrayList<>();
        conditions.add(qEvent.initiator.id.in(users));
//        conditions.add(qEvent.state.in(states));
        conditions.add(qEvent.category.id.in(categories));
        if (rangeStart != null) {
            conditions.add(qEvent.eventDate.before(rangeStart));
        }
        if (rangeEnd != null) {
            conditions.add(qEvent.eventDate.after(rangeEnd));
        }
        BooleanExpression commonCondition = conditions.stream()
                .reduce(BooleanExpression::and)
                .get();

        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        Collection<EventFullDto> eventDtos = eventMapper.convertToFullDtoCollection(
                eventRepository.findAll(commonCondition, page).getContent());
        log.info("Запрос списка событий по users = {}, states = {}, categories = {}, rangeStart = {}, rangeEnd = {} " +
                "- {}", users, states, categories, rangeStart, rangeEnd, eventDtos);
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
        conditions.add(qEvent.annotation.likeIgnoreCase(text));
        conditions.add(qEvent.description.likeIgnoreCase(text));
        conditions.add(qEvent.category.id.in(categories));
        conditions.add(qEvent.paid.eq(paid));
        conditions.add(qEvent.eventDate.before(rangeStart != null ? rangeStart : currentTime));
        if (rangeEnd != null) {
            conditions.add(qEvent.eventDate.after(rangeEnd));
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
            throw new IllegalStateException("Событие не является опубликованным");
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
        if (currentTime.plusHours(2).isBefore(newEventDto.getEventDate())) {
            log.info("Дата события должна быть запланирована за два часа до настоящего момента");
            throw new IllegalArgumentException();
        }

        User user = getUserByIdOrElseThrow(userId);
        Category category = getCategoryByIdOrElseThrow(newEventDto.getCategory());
        Event event = eventMapper.convertNewEventDtoToEntity(newEventDto);
        event.setEventDate(currentTime);
        event.setInitiator(user);
        event.setCategory(category);
        EventFullDto eventDto = eventMapper.convertToFullDto(eventRepository.save(event));
        log.info("Добавлено новое событие - {}", eventDto);
        return eventDto;
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
    public EventRequestStatusUpdateResult editEventRequest(long userId, long eventId,
                                                           EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        return null;
    }

    public enum EventSort {
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
        if (userRepository.existsById(userId)) {
            log.info("Пользователя по id = {} не существует", userId);
            throw new NotFoundException(String.format("Пользователя по id = %d не существует", userId));
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
