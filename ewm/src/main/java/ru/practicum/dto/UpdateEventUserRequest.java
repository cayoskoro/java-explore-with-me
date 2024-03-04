package ru.practicum.dto;

import lombok.Builder;
import lombok.Value;
import ru.practicum.model.EventState;

import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
public class UpdateEventUserRequest {
    private final String title;
    private final String annotation;
    private final Long category;
    private final boolean paid;
    private final LocalDateTime eventDate;
    private final String description;
    private final Integer participantLimit;
    private final EventState stateAction;
    private final boolean requestModeration;
    private final Location location;

    public boolean isNeedStateUpdate() {
        return stateAction != null;
    }

    public enum StateAction {
        SEND_TO_REVIEW, CANCEL_REVIEW
    }
}
