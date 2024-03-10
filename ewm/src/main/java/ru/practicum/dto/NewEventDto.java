package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
public class NewEventDto {
    @NotBlank
    @Size(min = 3, max = 120)
    private final String title;
    @Size(min = 20, max = 2000)
    @NotBlank
    private final String annotation;
    @NotNull
    @Positive
    private final Long category;
    private final boolean paid = false;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime eventDate;
    @Size(min = 20, max = 7000)
    @NotBlank
    private final String description;
    @Min(value = 0)
    private final Integer participantLimit = 0;
    private final boolean requestModeration = true;
    @NotNull
    private final Location location;
}
