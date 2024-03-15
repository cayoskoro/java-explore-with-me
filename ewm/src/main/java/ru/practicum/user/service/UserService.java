package ru.practicum.user.service;

import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDto;

import java.util.Collection;

public interface UserService {
    public Collection<UserDto> getAllUsers(Collection<Long> ids, int from, int size);

    public UserDto addNewUser(NewUserRequest newUserRequest);

    public void deleteUser(long userId);
}
