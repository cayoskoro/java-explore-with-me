package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
public class UpdateEventAdminRequest {
    @Size(min = 3, max = 120)
    private final String title;
    @Size(min = 20, max = 2000)
    private final String annotation;
    private final Long category;
    private final Boolean paid;
    @FutureOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime eventDate;
    @Size(min = 20, max = 7000)
    private final String description;
    @Min(value = 0)
    private final Integer participantLimit;
    private final StateAction stateAction;
    private final Boolean requestModeration;
    private final Location location;

    public enum StateAction {
        PUBLISH_EVENT, REJECT_EVENT
    }

}
