package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.control.DeepClone;

@Mapper(componentModel = "spring", mappingControl = DeepClone.class)
public interface CategoryMapper {

}
