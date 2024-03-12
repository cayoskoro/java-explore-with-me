package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.UpdateEventAdminRequest;
import ru.practicum.event.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
public class EventAdminController {
    private final EventService eventService;

    @GetMapping
    public Collection<EventFullDto> getAllEvents(@RequestParam(required = false) Collection<Long> users,
                                                 @RequestParam(required = false) Collection<String> states,
                                                 @RequestParam(required = false) Collection<Long> categories,
                                                 @RequestParam(required = false) LocalDateTime rangeStart,
                                                 @RequestParam(required = false) LocalDateTime rangeEnd,
                                                 @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                 @RequestParam(defaultValue = "10") @Positive int size) {
        return eventService.getAllEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto editEvent(@PathVariable long eventId,
                                  @RequestBody @Valid UpdateEventAdminRequest updateEventAdminRequest) {
        return eventService.editEvent(eventId, updateEventAdminRequest);
    }
}
