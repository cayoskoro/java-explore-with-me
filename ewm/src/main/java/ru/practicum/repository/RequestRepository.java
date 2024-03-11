package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.Request;

import java.util.Collection;

public interface RequestRepository extends JpaRepository<Request, Long> {
    public Collection<Request> findAllByRequesterId(long userId);

    public Request findByRequesterIdAndEventId(long userId, long eventId);
}
