package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.CategoryDto;
import ru.practicum.dto.NewCategoryDto;
import ru.practicum.service.CategoryService;

import java.util.Collection;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
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
    @Transactional
    public CategoryDto addNewCategory(NewCategoryDto newCategoryDto) {
        return null;
    }

    @Override
    @Transactional
    public void deleteCategory(long catId) {

    }

    @Override
    @Transactional
    public CategoryDto editCategory(long catId, NewCategoryDto newCategoryDto) {
        return null;
    }
}
