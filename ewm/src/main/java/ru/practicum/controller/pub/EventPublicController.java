package ru.practicum.controller.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EventFullDto;
import ru.practicum.dto.EventShortDto;
import ru.practicum.service.EventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequestMapping(path = "/events")
@RequiredArgsConstructor
public class EventPublicController {
    private final EventService eventService;

    @GetMapping
    public Collection<EventShortDto> getAllEvents(@RequestParam(required = false) String text,
                                                  @RequestParam(required = false) Collection<Long> categories,
                                                  @RequestParam(required = false) boolean paid,
                                                  @RequestParam(required = false) LocalDateTime rangeStart,
                                                  @RequestParam(required = false) LocalDateTime rangeEnd,
                                                  @RequestParam(required = false) boolean onlyAvailable,
                                                  @RequestParam(required = false) String sort,
                                                  @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                  @RequestParam(defaultValue = "10") @Positive int size) {
        return null;
    }

    @GetMapping("/{id}")
    public EventFullDto getEventById(@PathVariable long id) {
        return null;
    }
}
