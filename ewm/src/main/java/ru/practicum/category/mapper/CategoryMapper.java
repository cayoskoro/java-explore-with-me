package ru.practicum.category.mapper;

import org.mapstruct.*;
import org.mapstruct.control.DeepClone;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.model.Category;

import java.util.Collection;

@Mapper(componentModel = "spring", mappingControl = DeepClone.class)
public interface CategoryMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateCategoryFromNewCategoryDto(NewCategoryDto dto, @MappingTarget Category category);

    Category convertNewCategoryDtoToEntity(NewCategoryDto dto);

    CategoryDto convertToDto(Category entity);

    Collection<CategoryDto> convertToDtoCollection(Collection<Category> entities);

    Category clone(Category entity);
}
