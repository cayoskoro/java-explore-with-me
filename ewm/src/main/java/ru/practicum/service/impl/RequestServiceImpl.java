package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.ParticipationRequestDto;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.EventMapper;
import ru.practicum.mapper.RequestMapper;
import ru.practicum.mapper.UserMapper;
import ru.practicum.model.Event;
import ru.practicum.model.Request;
import ru.practicum.model.User;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.RequestRepository;
import ru.practicum.repository.UserRepository;
import ru.practicum.service.RequestService;

import java.util.Collection;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class RequestServiceImpl implements RequestService {
    private final RequestMapper requestMapper;
    private final EventMapper eventMapper;
    private final UserMapper userMapper;
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

        if (event.getInitiator().getId().equals(user.getId())) {
            throw new
        }

        return null;
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelRequest(long userId, long requestId) {
        return null;
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
