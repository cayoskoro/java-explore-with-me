package ru.practicum.user.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Value
@Builder(toBuilder = true)
public class UserDto {
    private final Long id;
    @NotBlank
    private final String name;
    @NotBlank
    private final String email;
}
