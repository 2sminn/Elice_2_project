package com.elice.kittyandpuppy.module.category.controller;

import com.elice.kittyandpuppy.module.category.dto.CategoryDto;
import com.elice.kittyandpuppy.module.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Long> saveCategory(@RequestBody CategoryDto categoryDto) {
        Long categoryId = categoryService.saveCategory(categoryDto);
        return new ResponseEntity<>(categoryId, HttpStatus.CREATED);
    }

    @GetMapping("/branch/{branch}")
    public ResponseEntity<CategoryDto> getCategoryByBranch(@PathVariable String branch) {
        CategoryDto category = categoryService.getFullCategoryTreeByBranch(branch);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Long> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryDto categoryDto) {
        Long updatedCategoryId = categoryService.updateCategory(categoryId, categoryDto);
        return new ResponseEntity<>(updatedCategoryId, HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId,
                                               @RequestParam(required = false, defaultValue = "false") boolean deleteSubcategories) {
        categoryService.deleteCategory(categoryId, deleteSubcategories);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
