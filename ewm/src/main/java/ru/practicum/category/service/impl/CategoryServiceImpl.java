package ru.practicum.category.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.category.service.CategoryService;
import ru.practicum.common.exception.NotFoundException;

import java.util.Collection;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public Collection<CategoryDto> getAllCategories(int from, int size) {
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        Collection<CategoryDto> categoryDtos = categoryMapper.convertToDtoCollection(categoryRepository.findAll(page)
                .getContent());
        log.info("Запрос списка всех категорий - {}", categoryDtos);
        return categoryDtos;
    }

    @Override
    public CategoryDto getCategoryById(long catId) {
        CategoryDto categoryDto = categoryMapper.convertToDto(getCategoryByIdOrElseThrow(catId));
        log.info("Запрос по id = {} категории - {}", catId, categoryDto);
        return categoryDto;
    }

    @Override
    @Transactional
    public CategoryDto addNewCategory(NewCategoryDto newCategoryDto) {
        Category category = categoryMapper.convertNewCategoryDtoToEntity(newCategoryDto);
        CategoryDto categoryDto = categoryMapper.convertToDto(categoryRepository.save(category));
        log.info("Добавлена новая категория - {}", categoryDto);
        return categoryDto;
    }

    @Override
    @Transactional
    public void deleteCategory(long catId) {
        checkCategoryIfExists(catId);
        categoryRepository.deleteById(catId);
        log.info("Категория по id = {} удалена", catId);
    }

    @Override
    @Transactional
    public CategoryDto editCategory(long catId, NewCategoryDto newCategoryDto) {
        Category category = getCategoryByIdOrElseThrow(catId);
        Category updatingCategory = categoryMapper.clone(category);
        categoryMapper.updateCategoryFromNewCategoryDto(newCategoryDto, updatingCategory);
        log.info("Категория подготовленная к обновлению - {}", updatingCategory);
        return categoryMapper.convertToDto(categoryRepository.save(updatingCategory));
    }

    private void checkCategoryIfExists(long catId) {
        if (!categoryRepository.existsById(catId)) {
            log.info("Категории по id = {} не существует", catId);
            throw new NotFoundException(String.format("Категории по id = %d не существует", catId));
        }
    }

    private Category getCategoryByIdOrElseThrow(long catId) {
        return categoryRepository.findById(catId)
                .orElseThrow(() -> {
                    log.info("Категории по id = {} не существует", catId);
                    return new NotFoundException(String.format("Категории по id = %d не существует", catId));
                });
    }
}
