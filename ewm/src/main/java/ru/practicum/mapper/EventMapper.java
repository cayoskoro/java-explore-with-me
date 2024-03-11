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
    @Mapping(target = "category.id", source = "category")
    void updateEventFromEventUserRequest(UpdateEventUserRequest dto, @MappingTarget Event event);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category.id", source = "category")
    void updateEventFromEventAdminRequest(UpdateEventAdminRequest dto, @MappingTarget Event event);

    @Mapping(target = "category.id", source = "category")
    @Mapping(target = "latitude", source = "location.lat")
    @Mapping(target = "longitude", source = "location.lon")
    Event convertNewEventDtoToEntity(NewEventDto dto);

    @Mapping(target = "confirmedRequests", ignore = true)
    EventFullDto convertToFullDto(Event entity);

    @Mapping(target = "confirmedRequests", ignore = true)
    EventShortDto convertToShortDto(Event entity);

    Collection<EventFullDto> convertToFullDtoCollection(Collection<Event> entities);

    Collection<EventShortDto> convertToShortDtoCollection(Collection<Event> entities);

    Event clone(Event entity);
}
