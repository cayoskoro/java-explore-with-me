package ru.practicum.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "events", schema = "public")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", length = 128, nullable = false)
    private String title;
    @Column(name = "annotation", length = 1024, nullable = false)
    private String annotation;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @Column(name = "paid", nullable = false)
    private boolean paid;
    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id", nullable = false)
    private User initiator;
    @Column(name = "description", length = 2048)
    private String description;
    @Column(name = "participant_limit")
    private Integer participantLimit;
    @Column(name = "state", length = 32, nullable = false)
    @Enumerated(EnumType.STRING)
    private EventState state;
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;
    @Column(name = "latitude", nullable = false)
    private Float latitude;
    @Column(name = "longitude", nullable = false)
    private Float longitude;
    @Column(name = "request_moderation")
    private boolean requestModeration;
}
