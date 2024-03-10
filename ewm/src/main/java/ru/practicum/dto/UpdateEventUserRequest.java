package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;
import ru.practicum.model.EventState;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
public class UpdateEventUserRequest {
    @Size(min = 3, max = 120)
    private final String title;
    @Size(min = 20, max = 2000)
    private final String annotation;
    private final Long category;
    private final boolean paid;
    @FutureOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime eventDate;
    @Size(min = 20, max = 7000)
    private final String description;
    @Min(value = 0)
    private final Integer participantLimit;
    private final StateAction stateAction;
    private final boolean requestModeration;
    private final Location location;

    public boolean isNeedStateUpdate() {
        return stateAction != null;
    }

    public enum StateAction {
        SEND_TO_REVIEW, CANCEL_REVIEW
    }
}
