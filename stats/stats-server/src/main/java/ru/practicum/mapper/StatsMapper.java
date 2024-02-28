package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.control.DeepClone;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.model.Stats;

@Mapper(componentModel = "spring", mappingControl = DeepClone.class)
public interface StatsMapper {
    Stats convertViewDtoToEntity(ViewStatsDto dto);

    ViewStatsDto convertToViewDto(Stats entity);
}
