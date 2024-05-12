package com.elice.kittyandpuppy.module.category.service;

import com.elice.kittyandpuppy.module.category.dto.RequestCategoryDto;
import com.elice.kittyandpuppy.module.category.dto.ResponseCategoryDto;
import com.elice.kittyandpuppy.module.category.entity.Category;
import com.elice.kittyandpuppy.module.category.repository.CategoryRepository;
import com.elice.kittyandpuppy.module.product.entity.Product;
import com.elice.kittyandpuppy.module.product.dto.ResponseProductDto;
import com.elice.kittyandpuppy.module.product.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductService productService;

    public CategoryService(CategoryRepository categoryRepository, ProductService productService) {
        this.categoryRepository = categoryRepository;
        this.productService = productService;
    }

    public Long saveCategory(RequestCategoryDto requestDto) {
        Category category = Category.fromDto(requestDto);
        category = categoryRepository.save(category);
        return category.getId();
    }

    public ResponseCategoryDto getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        return category.toDto();
    }

    public List<ResponseCategoryDto> getSubCategories(Long parentId) {
        List<Category> subCategories = categoryRepository.findByParentCategoryId(parentId);
        return subCategories.stream().map(Category::toDto).collect(Collectors.toList());
    }

    public List<ResponseProductDto> getCategoryProducts(Long categoryId) {
        return productService.findByCategoryId(categoryId).stream()
                .map(Product::toResponseDto)  // Product 클래스의 toResponseDto 메서드를 사용
                .collect(Collectors.toList());
    }

    public Long updateCategory(Long categoryId, RequestCategoryDto requestDto) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        existingCategory = Category.fromDto(requestDto); // assuming complete replacement
        existingCategory = categoryRepository.save(existingCategory);
        return existingCategory.getId();
    }

    public void deleteCategory(Long categoryId, boolean deleteSubcategories) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        if (deleteSubcategories) {
            category.getSubCategories().forEach(sub -> deleteCategory(sub.getId(), true));
        }
        categoryRepository.delete(category);
    }
}