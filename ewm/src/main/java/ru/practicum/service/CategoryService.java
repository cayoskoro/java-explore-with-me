package ru.practicum.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.CategoryDto;
import ru.practicum.dto.NewCategoryDto;

import java.util.Collection;

@Transactional(readOnly = true)
public interface CategoryService {
    public Collection<CategoryDto> getAllCategories(int from, int size);

    public CategoryDto getCategoryById(long catId);

    @Transactional
    public CategoryDto addNewCategory(NewCategoryDto newCategoryDto);

    @Transactional
    public void deleteCategory(long catId);

    @Transactional
    public CategoryDto editCategory(long catId, NewCategoryDto newCategoryDto);
}
