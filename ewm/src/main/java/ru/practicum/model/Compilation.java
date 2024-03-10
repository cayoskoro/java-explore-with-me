package ru.practicum.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "compilations", schema = "public")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", length = 128, nullable = false)
    private String title;
    @Column(name = "pinned", nullable = false)
    private boolean pinned;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "compilation_event",
            joinColumns = @JoinColumn(name = "compilation_id"), inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Collection<Event> events;
}
