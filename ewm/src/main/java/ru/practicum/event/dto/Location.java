package ru.practicum.event.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Location {
    private final Float lat;
    private final Float lon;
}
