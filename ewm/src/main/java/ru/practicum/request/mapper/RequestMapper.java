package ru.practicum.request.mapper;

import org.mapstruct.*;
import org.mapstruct.control.DeepClone;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.model.Request;

import java.util.Collection;

@Mapper(componentModel = "spring", mappingControl = DeepClone.class)
public interface RequestMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "event.id", source = "event")
    @Mapping(target = "requester.id", source = "requester")
    void updateRequestFromParticipationRequestDto(ParticipationRequestDto dto, @MappingTarget Request request);

    @Mapping(target = "event.id", source = "event")
    @Mapping(target = "requester.id", source = "requester")
    Request convertParticipationRequestDtoToEntity(ParticipationRequestDto dto);

    @Mapping(target = "event", source = "event.id")
    @Mapping(target = "requester", source = "requester.id")
    ParticipationRequestDto convertToParticipationRequestDto(Request entity);

    @Mapping(target = "event", source = "event.id")
    @Mapping(target = "requester", source = "requester.id")
    Collection<ParticipationRequestDto> convertToParticipationRequestDtoCollection(Collection<Request> entities);

    Request clone(Request entity);
}
