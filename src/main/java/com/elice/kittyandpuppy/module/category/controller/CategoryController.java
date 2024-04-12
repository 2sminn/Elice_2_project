package com.elice.kittyandpuppy.module.category.controller;

import com.elice.kittyandpuppy.module.category.dto.CategoryDto;
import com.elice.kittyandpuppy.module.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("api/categories")
    @ResponseBody
    public Long saveCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.saveCategory(categoryDto);
    }
    @GetMapping("api/categories/{branch}")
    @ResponseBody
    public Map<String, CategoryDto> getCategoryByBranch(@PathVariable String branch){
        return categoryService.getCategoryByBranch(branch);
    }

    @PutMapping("api/categories/{categoryId}")
    public Long updateCategory (@PathVariable Long categoryId, @RequestBody CategoryDto categoryDto) {
        return categoryService.updateCategory(categoryId, categoryDto);
    }

    @DeleteMapping ("api/categories/{categoryId}")
    public void deleteCategory (@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
