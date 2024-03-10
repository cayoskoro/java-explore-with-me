package ru.practicum.mapper;

import org.mapstruct.*;
import org.mapstruct.control.DeepClone;
import ru.practicum.dto.CompilationDto;
import ru.practicum.dto.NewCompilationDto;
import ru.practicum.dto.UpdateCompilationRequest;
import ru.practicum.model.Compilation;

import java.util.Collection;

@Mapper(componentModel = "spring", mappingControl = DeepClone.class)
public interface CompilationMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "events", ignore = true)
    void updateCompilationFromUpdateCompilationRequest(UpdateCompilationRequest dto,
                                                       @MappingTarget Compilation compilation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "events", ignore = true)
    Compilation convertNewCompilationDtoToEntity(NewCompilationDto dto);

    CompilationDto convertToDto(Compilation entity);

    Collection<CompilationDto> convertToDtoCollection(Collection<Compilation> entities);

    Compilation clone(Compilation entity);
}
