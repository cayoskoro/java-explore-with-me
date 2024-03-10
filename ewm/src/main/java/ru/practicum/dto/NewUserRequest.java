package ru.practicum.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@Builder(toBuilder = true)
public class NewUserRequest {
    @Size(min = 2, max = 250)
    @NotBlank
    private final String name;
    @Size(min = 6, max = 254)
    @NotBlank
    private final String email;
}
