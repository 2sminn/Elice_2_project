package com.elice.kittyandpuppy.module.category.controller;

import com.elice.kittyandpuppy.module.category.dto.CategoryDto;
import com.elice.kittyandpuppy.module.category.service.CategoryService;
import com.elice.kittyandpuppy.module.product.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
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

    // 강아지, 고양이 카테고리 정보 가져오기
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Long categoryId) {
        return new ResponseEntity<>(categoryService.getCategoryById(categoryId), HttpStatus.OK);
    }

    // 최상위 카테고리(강아지,고양이) 아래의 하위 카테고리 정보 가져오기
    @GetMapping("/{parentId}/subcategories")
    public ResponseEntity<List<CategoryDto>> getSubCategories(@PathVariable Long parentId) {
        return new ResponseEntity<>(categoryService.getSubCategories(parentId), HttpStatus.OK);
    }

    // 특정 카테고리(하위 카테고리)의 상품 정보 가져오기
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<List<ProductDto>> getCategoryProducts(@PathVariable Long categoryId) {
        return new ResponseEntity<>(categoryService.getCategoryProducts(categoryId), HttpStatus.OK);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Long> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryDto categoryDto) {
        Long updatedCategoryId = categoryService.updateCategory(categoryId, categoryDto);
        return new ResponseEntity<>(updatedCategoryId, HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId,
                                               @RequestParam(required = false, defaultValue = "false") boolean deleteSubcategories) {
        categoryService.deleteCategory(categoryId, deleteSubcategories);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
