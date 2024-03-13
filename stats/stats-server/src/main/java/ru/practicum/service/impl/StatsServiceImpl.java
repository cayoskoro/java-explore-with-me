package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.practicum.common.exception.DateNotValidException;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.mapper.HitMapper;
import ru.practicum.mapper.StatsMapper;
import ru.practicum.model.Hit;
import ru.practicum.model.Stats;
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
        log.info("Запрос статистики с параметрами: start = {}, end = {}, uris = {}, unique = {}", start, end, uris,
                unique);

        if (end.isBefore(start)) {
            throw new DateNotValidException("Значение end не должно быть раньше значения start");
        }
        if (start.isAfter(LocalDateTime.now())) {
            throw new DateNotValidException("Значение now не должно быть раньше значения start");
        }

        Collection<Stats> statsCollection;
        if (unique) {
            if (uris != null) {
                statsCollection = statsRepository.findAllByTimestampBetweenDatesAndUrisInWithUniqueIp(start, end, uris);
            } else {
                statsCollection = statsRepository.findAllByTimestampBetweenDatesWithUniqueIp(start, end);
            }
        } else {
            if (uris != null) {
                statsCollection = statsRepository.findAllByTimestampBetweenDatesAndUrisIn(start, end, uris);
            } else {
                statsCollection = statsRepository.findAllByTimestampBetweenDates(start, end);
            }
        }
        return statsMapper.convertToViewDtoCollection(statsCollection);
    }

    @Override
    @Transactional
    public EndpointHitDto addHit(EndpointHitDto endpointHitDto) {
        Hit hit = hitMapper.convertToEntity(endpointHitDto);
        log.info("Добавлен новый хит - {}", hit);
        return hitMapper.convertToDto(statsRepository.save(hit));
    }
}
