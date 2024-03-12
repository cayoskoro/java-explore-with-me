package ru.practicum.event.mapper;

import org.mapstruct.*;
import org.mapstruct.control.DeepClone;
import ru.practicum.event.dto.*;
import ru.practicum.event.model.Event;

import java.util.Collection;

@Mapper(componentModel = "spring", mappingControl = DeepClone.class)
public interface EventMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category.id", source = "category")
    void updateEventFromEventUserRequest(UpdateEventUserRequest dto, @MappingTarget Event event);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category.id", source = "category")
    void updateEventFromEventAdminRequest(UpdateEventAdminRequest dto, @MappingTarget Event event);

    @Mapping(target = "category.id", source = "category")
    @Mapping(target = "latitude", source = "location.lat")
    @Mapping(target = "longitude", source = "location.lon")
    @Mapping(target = "state", constant = "PENDING")
    Event convertNewEventDtoToEntity(NewEventDto dto);

    EventFullDto convertToFullDto(Event entity);

    EventShortDto convertToShortDto(Event entity);

    Collection<EventFullDto> convertToFullDtoCollection(Collection<Event> entities);

    Collection<EventShortDto> convertToShortDtoCollection(Collection<Event> entities);

    Event clone(Event entity);
}
