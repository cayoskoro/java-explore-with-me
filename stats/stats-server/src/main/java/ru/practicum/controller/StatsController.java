package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.service.StatsService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatsController {
    private final StatsService service;

    @GetMapping("/stats")
    public Collection<ViewStatsDto> getAllStats(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                                @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                                @RequestParam(required = false) Collection<String> uris,
                                                @RequestParam(required = false, defaultValue = "false") boolean unique) {
        return service.getAllStats(start, end, uris, unique);
    }

    @PostMapping("/hit")
    public void addHit(@RequestBody @Valid EndpointHitDto endpointHitDto) {
        service.addHit(endpointHitDto);
    }
}
