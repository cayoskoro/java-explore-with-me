package ru.practicum.compilation.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;

import java.util.Collection;

@Transactional(readOnly = true)
public interface CompilationService {
    public Collection<CompilationDto> getAllCompilations(Boolean pinned, int from, int size);

    public CompilationDto getCompilationById(long compId);

    @Transactional
    public CompilationDto addNewCompilation(NewCompilationDto newCompilationDto);

    @Transactional
    public void deleteCompilation(long compId);

    @Transactional
    public CompilationDto editCompilation(long compId, UpdateCompilationRequest updateCompilationRequest);
}
