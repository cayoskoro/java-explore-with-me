package ru.practicum.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class NewUserRequest {
    private final String name;
    private final String email;
}
