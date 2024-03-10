package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.CompilationDto;
import ru.practicum.dto.NewCompilationDto;
import ru.practicum.dto.UpdateCompilationRequest;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.CompilationMapper;
import ru.practicum.model.Compilation;
import ru.practicum.model.Event;
import ru.practicum.model.User;
import ru.practicum.repository.CompilationRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.service.CompilationService;

import java.util.Collection;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CompilationServiceImpl implements CompilationService {
    private final CompilationMapper compilationMapper;
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    public Collection<CompilationDto> getAllCompilations(boolean pinned, int from, int size) {
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        Collection<CompilationDto> compilationDtos = compilationMapper.convertToDtoCollection(
                compilationRepository.findAllByPinnedIs(pinned, page).getContent());
        log.info("Запрос подборок pinned = {} событий - {}", pinned, compilationDtos);
        return compilationDtos;
    }

    @Override
    public CompilationDto getCompilationById(long compId) {
        Compilation compilation = getCompilationByIdOrElseThrow(compId);
        CompilationDto compilationDto = compilationMapper.convertToDto(compilation);
        log.info("Запрошена подборка событий по id = {} - {}", compId, compilationDto);
        return compilationDto;
    }

    @Override
    @Transactional
    public CompilationDto addNewCompilation(NewCompilationDto newCompilationDto) {
        Collection<Event> events = eventRepository.findAllById(newCompilationDto.getEvents());
        Compilation compilation = compilationMapper.convertNewCompilationDtoToEntity(newCompilationDto);
        compilation.setEvents(events);
        CompilationDto compilationDto = compilationMapper.convertToDto(compilationRepository.save(compilation));
        log.info("Добавлена новая подборка событий - {}", compilationDto);
        return compilationDto;
    }

    @Override
    @Transactional
    public void deleteCompilation(long compId) {
        checkCompilationIfExists(compId);
        compilationRepository.deleteById(compId);
        log.info("Подборка событий по id = {} удалена", compId);
    }

    @Override
    @Transactional
    public CompilationDto editCompilation(long compId, UpdateCompilationRequest updateCompilationRequest) {
        Compilation compilation = getCompilationByIdOrElseThrow(compId);
        Compilation updatingCompilation = compilationMapper.clone(compilation);
        compilationMapper.updateCompilationFromUpdateCompilationRequest(updateCompilationRequest, updatingCompilation);
        Collection<Event> events = eventRepository.findAllById(updateCompilationRequest.getEvents());
        updatingCompilation.setEvents(events);
        log.info("Подборка событий подготовлена к обновлению - {}", updatingCompilation);
        return compilationMapper.convertToDto(compilationRepository.save(updatingCompilation));
    }

    private Compilation getCompilationByIdOrElseThrow(long compId) {
        return compilationRepository.findById(compId)
                .orElseThrow(() -> {
                    log.info("Подборки событий по id = {} не существует", compId);
                    return new NotFoundException(String.format("Подборки событий по id = %d не существует", compId));
                });
    }

    private void checkCompilationIfExists(long compId) {
        if (!compilationRepository.existsById(compId)) {
            log.info("Подборки событий по id = {} не существует", compId);
            throw new NotFoundException(String.format("Подборки событий по id = %d не существует", compId));
        }
    }
}
