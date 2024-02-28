package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.control.DeepClone;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.model.Hit;

@Mapper(componentModel = "spring", mappingControl = DeepClone.class)
public interface HitMapper {
    Hit convertToEntity(EndpointHitDto dto);

    EndpointHitDto convertToDto(Hit entity);
}
