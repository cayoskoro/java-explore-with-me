package ru.practicum.request.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.common.exception.ConflictException;
import ru.practicum.common.exception.NotFoundException;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventState;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.mapper.RequestMapper;
import ru.practicum.request.model.Request;
import ru.practicum.request.model.RequestStatus;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.request.service.RequestService;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class RequestServiceImpl implements RequestService {
    private final RequestMapper requestMapper;
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public Collection<ParticipationRequestDto> getAllRequests(long userId) {
        checkUserIfExists(userId);

        Collection<ParticipationRequestDto> participationRequestDtos = requestMapper
                .convertToParticipationRequestDtoCollection(requestRepository.findAllByRequesterId(userId));
        log.info("Запрос заявок на участие в чужих событиях пользователем по id = {} - {}",
                userId, participationRequestDtos);
        return participationRequestDtos;
    }

    @Override
    @Transactional
    public ParticipationRequestDto addNewRequest(long userId, long eventId) {
        User user = getUserByIdOrElseThrow(userId);
        Event event = getEventByIdOrElseThrow(eventId);

        throwIfUserIsRequestInitiator(event, user);
        throwIfRepeatedRequest(event, user);
        throwIfNotPublishedEvent(event);
        throwIfParticipantLimitOver(event);

        Request request = Request.builder()
                .event(event)
                .requester(user)
                .status(event.getParticipantLimit() != 0 && event.isRequestModeration() ? RequestStatus.PENDING :
                        RequestStatus.CONFIRMED)
                .created(LocalDateTime.now())
                .build();

        if (request.getStatus().equals(RequestStatus.CONFIRMED)) {
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventRepository.save(event);
            log.info("Количество подтвержденных запросов на участие для события по id = {} " +
                    "увеличилось после подтверждения запроса на участие по id = {}", event.getId(), request.getId());
        }

        ParticipationRequestDto participationRequestDto = requestMapper.convertToParticipationRequestDto(
                requestRepository.save(request));
        log.info("Добавлен запрос на участие в событии - {}", participationRequestDto);
        return participationRequestDto;
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelRequest(long userId, long requestId) {
        checkUserIfExists(userId);
        Request request = getRequestByIdOrElseThrow(requestId);

        throwIfUserIsRequestInitiator(request, userId);

        if (request.getStatus().equals(RequestStatus.CONFIRMED)) {
            Event event = request.getEvent();
            event.setConfirmedRequests(event.getConfirmedRequests() - 1);
            eventRepository.save(event);
            log.info("Количество подтвержденных запросов на участие для события по id = {} " +
                    "уменьшилось после отмены запроса на участие по id = {}", event.getId(), requestId);
        }

        request.setStatus(RequestStatus.CANCELED);
        log.info("Запрос на участие после изменения статуса на CANCELED подготовлен к обновлению - {}", request);
        return requestMapper.convertToParticipationRequestDto(requestRepository.save(request));
    }

    private void throwIfUserIsRequestInitiator(Request request, long userId) {
        if (!request.getRequester().getId().equals(userId)) {
            log.info("Пользователь по id = {} не является владельцем запроса по id = {}, " +
                    "следовательно, отмена невозможна", userId, request.getRequester().getId());
            throw new ConflictException(String.format("Пользователь по id = %d не является владельцем " +
                    "запроса по id = %d, следовательно, отмена невозможна", userId, request.getRequester().getId()));
        }
    }

    private void throwIfUserIsRequestInitiator(Event event, User user) {
        if (event.getInitiator().getId().equals(user.getId())) {
            log.info("Инициатор события по id = {} не может добавить запрос на участие в своём событии по id = {}",
                    user.getId(), event.getId());
            throw new ConflictException(String.format("Инициатор события по id = %d не может добавить запрос" +
                    " на участие в своём событии по id = %d", user.getId(), event.getId()));
        }
    }

    private void throwIfParticipantLimitOver(Event event) {
        if (event.getParticipantLimit() != 0 && event.getConfirmedRequests() >= event.getParticipantLimit()) {
            log.info("Достигнут лимит запросов на участие participantLimit = {}", event.getParticipantLimit());
            throw new ConflictException("Достигнут лимит запросов на участие participantLimit = " +
                    event.getParticipantLimit());
        }
    }

    private void throwIfRepeatedRequest(Event event, User user) {
        if (requestRepository.findByRequesterIdAndEventId(user.getId(), event.getId()) != null) {
            log.info("Нельзя добавить повторный запрос userId = {}, eventId = {}", user.getId(), event.getId());
            throw new ConflictException(String.format("Нельзя добавить повторный запрос userId = %d, eventId = %d",
                    user.getId(), event.getId()));
        }
    }

    private void throwIfNotPublishedEvent(Event event) {
        if (!event.getState().equals(EventState.PUBLISHED)) {
            log.info("Нельзя участвовать в неопубликованном событии - {}", event);
            throw new ConflictException("Нельзя участвовать в неопубликованном событии - " + event);
        }
    }

    private Request getRequestByIdOrElseThrow(long requestId) {
        return requestRepository.findById(requestId)
                .orElseThrow(() -> {
                    log.info("Запроса на участие по id = {} не существует", requestId);
                    return new NotFoundException(String.format("Запроса на участие по id = %d не существует",
                            requestId));
                });
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

    private User getUserByIdOrElseThrow(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.info("Пользователя по id = {} не существует", userId);
                    return new NotFoundException(String.format("Пользователя по id = %d не существует", userId));
                });
    }
}
