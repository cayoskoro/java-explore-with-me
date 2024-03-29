package ru.practicum.request.model;

import lombok.*;
import ru.practicum.event.model.Event;
import ru.practicum.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "requests", schema = "public")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;
    @Column(name = "status", length = 32, nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
    @Column(name = "created", nullable = false)
    private LocalDateTime created;
}
