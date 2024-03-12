package ru.practicum.user.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDto;

import java.util.Collection;

@Transactional(readOnly = true)
public interface UserService {
    public Collection<UserDto> getAllUsers(Collection<Long> ids, int from, int size);

    @Transactional
    public UserDto addNewUser(NewUserRequest newUserRequest);

    @Transactional
    public void deleteUser(long userId);
}
