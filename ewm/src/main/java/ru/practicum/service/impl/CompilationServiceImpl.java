package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.CompilationDto;
import ru.practicum.dto.NewCompilationDto;
import ru.practicum.dto.UpdateCompilationRequest;
import ru.practicum.service.CompilationService;

import java.util.Collection;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CompilationServiceImpl implements CompilationService {
    @Override
    public Collection<CompilationDto> getAllCompilations(boolean pinned, int from, int size) {
        return null;
    }

    @Override
    public CompilationDto getCompilationById(long compId) {
        return null;
    }

    @Override
    @Transactional
    public CompilationDto addNewCompilation(NewCompilationDto newCompilationDto) {
        return null;
    }

    @Override
    @Transactional
    public void deleteCompilation(long compId) {

    }

    @Override
    @Transactional
    public CompilationDto editCompilation(long compId, UpdateCompilationRequest updateCompilationRequest) {
        return null;
    }
}
