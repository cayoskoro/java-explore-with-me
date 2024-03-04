package ru.practicum.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.CompilationDto;
import ru.practicum.dto.NewCompilationDto;
import ru.practicum.dto.UpdateCompilationRequest;

import java.util.Collection;

@Transactional(readOnly = true)
public interface CompilationService {
    public Collection<CompilationDto> getAllCompilations(boolean pinned, int from, int size);

    public CompilationDto getCompilationById(long compId);

    @Transactional
    public CompilationDto addNewCompilation(NewCompilationDto newCompilationDto);

    @Transactional
    public void deleteCompilation(long compId);

    @Transactional
    public CompilationDto editCompilation(long compId, UpdateCompilationRequest updateCompilationRequest);
}
