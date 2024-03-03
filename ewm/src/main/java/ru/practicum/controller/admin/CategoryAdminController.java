package ru.practicum.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CategoryDto;
import ru.practicum.dto.NewCategoryDto;
import ru.practicum.service.CategoryService;

@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
public class CategoryAdminController {
    private final CategoryService categoryService;

    @PostMapping
    public CategoryDto addNewCategory(@RequestBody NewCategoryDto newCategoryDto) {
        return categoryService.addNewCategory(newCategoryDto);
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable long catId) {
        categoryService.deleteCategory(catId);
    }

    @PatchMapping("/{catId}")
    public CategoryDto editCategory(@PathVariable long catId, @RequestBody NewCategoryDto newCategoryDto) {
        return categoryService.editCategory(catId, newCategoryDto);
    }
}
