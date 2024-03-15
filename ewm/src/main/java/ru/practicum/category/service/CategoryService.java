package ru.practicum.category.service;

import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;

import java.util.Collection;

public interface CategoryService {
    public Collection<CategoryDto> getAllCategories(int from, int size);

    public CategoryDto getCategoryById(long catId);

    public CategoryDto addNewCategory(NewCategoryDto newCategoryDto);

    public void deleteCategory(long catId);

    public CategoryDto editCategory(long catId, NewCategoryDto newCategoryDto);
}
