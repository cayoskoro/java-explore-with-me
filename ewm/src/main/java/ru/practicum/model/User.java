package ru.practicum.model;

import lombok.*;

import javax.persistence.*;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "users", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", length = 64, nullable = false)
    private String name;
    @Column(name = "email", length = 64, nullable = false, unique = true)
    private String email;
}
