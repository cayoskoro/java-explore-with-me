package ru.practicum.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
public class UpdateEventAdminRequest {
    private final String title;
    private final String annotation;
    private final Long category;
    private final boolean paid;
    private final LocalDateTime eventDate;
    private final String description;
    private final Integer participantLimit;
    private final StateAction stateAction;
    private final boolean requestModeration;
    private final Location location;

    public boolean isNeedStateUpdate() {
        return stateAction != null;
    }

    public enum StateAction {
        PUBLISH_EVENT, REJECT_EVENT
    }

}
