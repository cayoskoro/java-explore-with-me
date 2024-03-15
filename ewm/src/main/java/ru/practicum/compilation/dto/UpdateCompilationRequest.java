package ru.practicum.compilation.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Size;
import java.util.Collection;

@Value
@Builder(toBuilder = true)
public class UpdateCompilationRequest {
    @Size(min = 1, max = 50)
    private final String title;
    private final Boolean pinned;
    private final Collection<Long> events;
}
