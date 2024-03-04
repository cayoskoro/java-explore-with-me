package ru.practicum.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Collection;

@Value
@Builder(toBuilder = true)
public class CompilationDto {
    private final Long id;
    private final String title;
    private final boolean pinned;
    private final Collection<EventShortDto> events;
}
