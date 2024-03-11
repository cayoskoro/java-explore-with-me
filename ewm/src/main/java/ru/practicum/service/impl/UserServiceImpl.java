package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.NewUserRequest;
import ru.practicum.dto.UserDto;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.UserMapper;
import ru.practicum.model.User;
import ru.practicum.repository.UserRepository;
import ru.practicum.service.UserService;

import java.util.Collection;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public Collection<UserDto> getAllUsers(Collection<Long> ids, int from, int size) {
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        Page<User> userPage = ids == null ? userRepository.findAll(page) : userRepository.findByIdIn(ids, page);
        Collection<UserDto> userDtos = userMapper.convertToDtoCollection(userPage.getContent());
        log.info("Запрос списка пользователей из ids = {} - {}", ids, userDtos);
        return userDtos;
    }

    @Override
    @Transactional
    public UserDto addNewUser(NewUserRequest newUserRequest) {
        User user = userMapper.convertNewUserRequestToEntity(newUserRequest);
        UserDto userDto = userMapper.convertToDto(userRepository.save(user));
        log.info("Добавлен новый пользователь - {}", userDto);
        return userDto;
    }

    @Override
    @Transactional
    public void deleteUser(long userId) {
        checkUserIfExists(userId);
        userRepository.deleteById(userId);
        log.info("Пользователь по id = {} удален", userId);
    }

    private void checkUserIfExists(long userId) {
        if (!userRepository.existsById(userId)) {
            log.info("Пользователя по id = {} не существует", userId);
            throw new NotFoundException(String.format("Пользователя по id = %d не существует", userId));
        }
    }
}
