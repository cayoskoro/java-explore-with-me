package ru.practicum.mapper;

import org.mapstruct.*;
import org.mapstruct.control.DeepClone;
import ru.practicum.dto.*;
import ru.practicum.model.Event;

import java.util.Collection;

@Mapper(componentModel = "spring", mappingControl = DeepClone.class)
public interface EventMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEventFromNewEventDto(NewEventDto dto, @MappingTarget Event event);

    Event convertNewEventDtoToEntity(NewEventDto dto);

    EventFullDto convertToFullDto(Event entity);

    EventShortDto convertToShortDto(Event entity);

    Collection<EventFullDto> convertToFullDtoCollection(Collection<Event> entities);

    Collection<EventShortDto> convertToShortDtoCollection(Collection<Event> entities);

    Event clone(Event entity);
}
