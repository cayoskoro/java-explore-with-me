package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.NewUserRequest;
import ru.practicum.dto.UserDto;
import ru.practicum.service.UserService;

import java.util.Collection;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    @Override
    public Collection<UserDto> getAllUsers(Collection<Long> ids, int from, int size) {
        return null;
    }

    @Override
    @Transactional
    public UserDto addNewUser(NewUserRequest newUserRequest) {
        return null;
    }

    @Override
    @Transactional
    public void deleteUser(long userId) {

    }
}
