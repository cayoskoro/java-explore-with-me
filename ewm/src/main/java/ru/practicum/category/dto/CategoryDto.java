package ru.practicum.category.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class CategoryDto {
    private final Long id;
    private final String name;
}
