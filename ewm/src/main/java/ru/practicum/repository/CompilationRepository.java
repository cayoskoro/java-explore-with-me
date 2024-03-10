package ru.practicum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    public Page<Compilation> findAllByPinnedIs(boolean pinned, Pageable page);
}
