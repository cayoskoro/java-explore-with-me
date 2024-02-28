package ru.practicum.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ErrorResponse {
    private final String error;
    @NotBlank
    private final String description;
}
