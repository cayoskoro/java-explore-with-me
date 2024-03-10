package ru.practicum.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@Builder(toBuilder = true)
public class NewCategoryDto {
    @Size(min = 1, max = 50)
    @NotBlank
    private final String name;
}
