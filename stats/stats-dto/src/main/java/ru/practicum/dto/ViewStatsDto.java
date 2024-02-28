package ru.practicum.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class ViewStatsDto {
    private final String app;
    private final String uri;
    private final Long hits;
}
