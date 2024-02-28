package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.mapper.HitMapper;
import ru.practicum.mapper.StatsMapper;
import ru.practicum.model.Hit;
import ru.practicum.repository.StatsRepository;
import ru.practicum.service.StatsService;

import java.time.LocalDateTime;
import java.util.Collection;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {
    private final HitMapper hitMapper;
    private final StatsMapper statsMapper;
    private final StatsRepository statsRepository;

    @Override
    public Collection<ViewStatsDto> getAllStats(LocalDateTime start, LocalDateTime end, Collection<String> uris,
                                                boolean unique) {
        return null;
    }

    @Override
    @Transactional
    public EndpointHitDto addHit(EndpointHitDto endpointHitDto) {
        Hit hit = hitMapper.convertToEntity(endpointHitDto);
        log.info("Добавлен новый хит - {}", hit);
        return hitMapper.convertToDto(statsRepository.save(hit));
    }
}
