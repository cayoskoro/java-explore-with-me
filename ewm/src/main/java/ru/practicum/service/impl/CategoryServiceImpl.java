package ru.practicum.service.impl;

import ru.practicum.dto.CategoryDto;
import ru.practicum.dto.NewCategoryDto;
import ru.practicum.service.CategoryService;

import java.util.Collection;

public class CategoryServiceImpl implements CategoryService {
    @Override
    public Collection<CategoryDto> getAllCategories(int from, int size) {
        return null;
    }

    @Override
    public CategoryDto getCategoryById(long catId) {
        return null;
    }

    @Override
    public CategoryDto addNewCategory(NewCategoryDto newCategoryDto) {
        return null;
    }

    @Override
    public void deleteCategory(long catId) {

    }

    @Override
    public CategoryDto editCategory(long catId, NewCategoryDto newCategoryDto) {
        return null;
    }
}
