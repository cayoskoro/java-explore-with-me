package ru.practicum.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.Collection;

@Transactional(readOnly = true)
public interface StatsService {
    public Collection<ViewStatsDto> getAllStats(LocalDateTime start, LocalDateTime end, Collection<String> uris,
                                                boolean unique);

    public EndpointHitDto addHit(EndpointHitDto endpointHitDto);
}
