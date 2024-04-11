package com.elice.kittyandpuppy.module.category.controller;

import com.elice.kittyandpuppy.module.category.dto.CategoryDto;
import com.elice.kittyandpuppy.module.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/categories")
    @ResponseBody
    public Long saveCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.saveCategory(categoryDto);
    }
    @GetMapping("/categories/{branch}")
    @ResponseBody
    public Map<String, CategoryDto> getCategoryByBranch(@PathVariable String branch){
        return categoryService.getCategoryByBranch(branch);
    }

    @PutMapping("/categories/{categoryId}")
    public Long updateCategory (@PathVariable Long categoryId, @RequestBody CategoryDto categoryDto) {
        return categoryService.updateCategory(categoryId, categoryDto);
    }

    @DeleteMapping ("/categories/{categoryId}")
    public void deleteCategory (@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
