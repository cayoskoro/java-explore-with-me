package ru.practicum.controller.priv;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.*;
import ru.practicum.service.EventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

@RestController
@RequestMapping(path = "/users/{userId}/events")
@RequiredArgsConstructor
public class EventPrivateController {
    private final EventService eventService;

    @GetMapping
    public Collection<EventShortDto> getAllEvents(@PathVariable long userId,
                                                  @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                  @RequestParam(defaultValue = "10") @Positive int size) {
        return null;
    }

    @PostMapping
    public EventFullDto addNewEvent(@PathVariable long userId, @RequestBody NewEventDto newEventDto) {
        return null;
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventById(@PathVariable long userId, @PathVariable long eventId) {
        return null;
    }

    @PatchMapping("/{eventId}")
    public EventFullDto editEvent(@PathVariable long userId, @PathVariable long eventId,
                                  @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        return null;
    }

    @GetMapping("/{eventId}/requests")
    public Collection<ParticipationRequestDto> getEventRequestsById(@PathVariable long userId,
                                                                    @PathVariable long eventId) {
        return null;
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult editEventRequest(@PathVariable long userId, @PathVariable long eventId,
                                                           @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        return null;
    }
}
