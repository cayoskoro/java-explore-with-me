package ru.practicum.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
public class EndpointHitDto {
    private final Long id;
    @NotBlank
    private final String app;
    @NotNull
    private final String uri;
    @NotBlank
    private final String ip;
    @NotNull
    private final LocalDateTime timestamp;

}
