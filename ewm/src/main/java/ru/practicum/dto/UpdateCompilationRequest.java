package ru.practicum.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Collection;

@Value
@Builder(toBuilder = true)
public class UpdateCompilationRequest {
    private final String title;
    private final boolean pinned;
    private final Collection<Long> events;
}
