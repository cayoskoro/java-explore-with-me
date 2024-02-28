package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.model.Hit;
import ru.practicum.model.Stats;

import java.time.LocalDateTime;
import java.util.Collection;

public interface StatsRepository extends JpaRepository<Hit, Long> {
    @Query(value = "SELECT new ru.practicum.model.Stats(h.app, h.uri, COUNT(h.ip)) " +
            "FROM Hit AS h " +
            "WHERE h.timestamp BETWEEN ?1 AND ?2 AND h.uri IN ?3 " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.ip) DESC")
    public Collection<Stats> findAllByTimestampBetweenDatesAndUrisIn(LocalDateTime start, LocalDateTime end,
                                                                     Collection<String> uris);

    @Query(value = "SELECT new ru.practicum.model.Stats(h.app, h.uri, COUNT(DISTINCT h.ip)) " +
            "FROM Hit AS h " +
            "WHERE h.timestamp BETWEEN ?1 AND ?2 AND h.uri IN ?3 " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.ip) DESC")
    public Collection<Stats> findAllByTimestampBetweenDatesAndUrisInWithUniqueIp(LocalDateTime start, LocalDateTime end,
                                                                     Collection<String> uris);

    @Query(value = "SELECT new ru.practicum.model.Stats(h.app, h.uri, COUNT(h.ip)) " +
            "FROM Hit AS h " +
            "WHERE h.timestamp BETWEEN ?1 AND ?2 " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.ip) DESC")
    public Collection<Stats> findAllByTimestampBetweenDates(LocalDateTime start, LocalDateTime end);

    @Query(value = "SELECT new ru.practicum.model.Stats(h.app, h.uri, COUNT(DISTINCT h.ip)) " +
            "FROM Hit AS h " +
            "WHERE h.timestamp BETWEEN ?1 AND ?2 " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.ip) DESC")
    public Collection<Stats> findAllByTimestampBetweenDatesWithUniqueIp(LocalDateTime start, LocalDateTime end);

}
