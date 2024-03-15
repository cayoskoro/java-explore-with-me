package ru.practicum.compilation.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.common.exception.NotFoundException;
import ru.practicum.common.util.Constants;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;
import ru.practicum.compilation.mapper.CompilationMapper;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.repository.CompilationRepository;
import ru.practicum.compilation.service.CompilationService;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;

import java.util.Collection;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CompilationServiceImpl implements CompilationService {
    private final CompilationMapper compilationMapper;
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    public Collection<CompilationDto> getAllCompilations(Boolean pinned, int from, int size) {
        PageRequest page = Constants.getDefaultPageRequest(from, size);
        Collection<Compilation> compilations = pinned != null ?
                compilationRepository.findAllByPinnedIs(pinned, page).getContent() :
                compilationRepository.findAll(page).getContent();
        Collection<CompilationDto> compilationDtos = compilationMapper.convertToDtoCollection(compilations);
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
        if (updateCompilationRequest.getEvents() != null) {
            Collection<Event> events = eventRepository.findAllById(updateCompilationRequest.getEvents());
            updatingCompilation.setEvents(events);
        }
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
