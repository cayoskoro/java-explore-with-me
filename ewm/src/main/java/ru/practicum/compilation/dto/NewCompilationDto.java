package ru.practicum.compilation.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;

@Value
@Builder(toBuilder = true)
public class NewCompilationDto {
    @Size(min = 1, max = 50)
    @NotBlank
    private final String title;
    private final boolean pinned = false;
    @NotNull
    private final Collection<Long> events = new HashSet<>();
}
