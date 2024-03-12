package ru.practicum.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.request.model.Request;
import ru.practicum.request.model.RequestStatus;

import java.util.Collection;

public interface RequestRepository extends JpaRepository<Request, Long> {
    public Collection<Request> findAllByRequesterId(long userId);

    public Request findByRequesterIdAndEventId(long userId, long eventId);

    public Collection<Request> findAllByEventId(long eventId);

    public Collection<Request> findAllByIdInAndStatus(Collection<Long> requestId, RequestStatus status);
}
