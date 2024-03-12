package ru.practicum.user.mapper;

import org.mapstruct.*;
import org.mapstruct.control.DeepClone;
import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.model.User;

import java.util.Collection;

@Mapper(componentModel = "spring", mappingControl = DeepClone.class)
public interface UserMapper {
    User convertNewUserRequestToEntity(NewUserRequest dto);

    UserDto convertToDto(User entity);

    Collection<UserDto> convertToDtoCollection(Collection<User> entities);
}
