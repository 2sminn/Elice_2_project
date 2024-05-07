package com.elice.kittyandpuppy.module.category.controller;

import com.elice.kittyandpuppy.module.category.dto.RequestCategoryDto;
import com.elice.kittyandpuppy.module.category.dto.ResponseCategoryDto;
import com.elice.kittyandpuppy.module.category.service.CategoryService;
import com.elice.kittyandpuppy.module.product.dto.ResponseProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Long> saveCategory(@RequestBody RequestCategoryDto requestCategoryDto) {
        Long categoryId = categoryService.saveCategory(requestCategoryDto);
        return new ResponseEntity<>(categoryId, HttpStatus.CREATED);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ResponseCategoryDto> getCategory(@PathVariable("categoryId") Long categoryId) {
        ResponseCategoryDto category = categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping("/{parentId}/subcategories")
    public ResponseEntity<List<ResponseCategoryDto>> getSubCategories(@PathVariable("parentId") Long parentId) {
        List<ResponseCategoryDto> subCategories = categoryService.getSubCategories(parentId);
        return new ResponseEntity<>(subCategories, HttpStatus.OK);
    }

    // 특정 카테고리(하위 카테고리)의 상품 정보 가져오기
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<List<ResponseProductDto>> getCategoryProducts(@PathVariable("categoryId") Long categoryId) {
        List<ResponseProductDto> products = categoryService.getCategoryProducts(categoryId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Long> updateCategory(@PathVariable("categoryId") Long categoryId, @RequestBody RequestCategoryDto requestCategoryDto) {
        Long updatedCategoryId = categoryService.updateCategory(categoryId, requestCategoryDto);
        return new ResponseEntity<>(updatedCategoryId, HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("categoryId") Long categoryId, @RequestParam(required = false, defaultValue = "false") boolean deleteSubcategories) {
        categoryService.deleteCategory(categoryId, deleteSubcategories);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}