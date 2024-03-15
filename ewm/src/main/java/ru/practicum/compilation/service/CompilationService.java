package ru.practicum.compilation.service;

import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;

import java.util.Collection;

public interface CompilationService {
    public Collection<CompilationDto> getAllCompilations(Boolean pinned, int from, int size);

    public CompilationDto getCompilationById(long compId);

    public CompilationDto addNewCompilation(NewCompilationDto newCompilationDto);

    public void deleteCompilation(long compId);

    public CompilationDto editCompilation(long compId, UpdateCompilationRequest updateCompilationRequest);
}
