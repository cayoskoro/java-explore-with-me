package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.Request;
import ru.practicum.model.RequestStatus;

import java.util.Collection;

public interface RequestRepository extends JpaRepository<Request, Long> {
    public Collection<Request> findAllByRequesterId(long userId);

    public Request findByRequesterIdAndEventId(long userId, long eventId);

    public Collection<Request> findAllByEventId(long eventId);

    public Collection<Request> findAllByIdInAndStatus(Collection<Long> requestId, RequestStatus status);
}
