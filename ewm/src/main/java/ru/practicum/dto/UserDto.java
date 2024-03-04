package ru.practicum.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class UserDto {
    private final Long id;
    private final String name;
    private final String email;
}
