package ru.practicum.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.user.model.User;

import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Long> {
    public Page<User> findByIdIn(Collection<Long> ids, Pageable page);
}
