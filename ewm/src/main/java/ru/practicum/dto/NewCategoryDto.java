package ru.practicum.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class NewCategoryDto {
    private final String name;
}
