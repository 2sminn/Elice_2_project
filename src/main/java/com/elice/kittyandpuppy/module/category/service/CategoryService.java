package com.elice.kittyandpuppy.module.category.service;

import com.elice.kittyandpuppy.module.category.entity.Category;
import com.elice.kittyandpuppy.module.category.dto.CategoryDto;
import com.elice.kittyandpuppy.module.category.dto.CategoryMapper;
import com.elice.kittyandpuppy.module.category.repository.CategoryRepository;
import com.elice.kittyandpuppy.module.product.dto.ProductDto;
import com.elice.kittyandpuppy.module.product.dto.ProductMapper;
import com.elice.kittyandpuppy.module.product.entity.Product;
import com.elice.kittyandpuppy.module.product.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Setter
@Getter
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ProductService productService;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper, ProductService productService) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.productService = productService;
    }
    public Long saveCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        if (categoryDto.getParentCategoryId() == null) {
            category.setParentCategory(null);
        } else {
            Category parentCategory = categoryRepository.findById(categoryDto.getParentCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 카테고리를 찾을 수 없습니다."));
            category.setParentCategory(parentCategory);
        }
        category = categoryRepository.save(category);
        return category.getId();
    }
    // 주어진 branch의 최상위 카테고리 및 모든 하위 카테고리 가져오기
    public CategoryDto getFullCategoryTreeByBranch(String branch) {
        Category topCategory = categoryRepository.findByBranch(branch)
                .orElseThrow(() -> new IllegalArgumentException("해당 branch의 최상위 카테고리를 찾을 수 없습니다: " + branch));
        return buildCategoryDtoWithSubcategories(topCategory);
    }
    // 주어진 categoryId의 하위 카테고리와 상품을 포함하는 CategoryDto 생성
    private CategoryDto buildCategoryDtoWithSubcategories(Category category) {
        CategoryDto categoryDto = categoryMapper.toDto(category);
        List<Category> subCategories = categoryRepository.findByParentCategoryId(category.getId());
        List<CategoryDto> subCategoryDtos = subCategories.stream()
                .map(this::buildCategoryDtoWithSubcategories)
                .collect(Collectors.toList());
        categoryDto.setSubCategories(subCategoryDtos);
        List<ProductDto> products = productService.findByCategoryId(category.getId()).stream()
                .map(productService.getProductMapper()::toDto)  // 수정: productService에서 productMapper 접근
                .collect(Collectors.toList());
        categoryDto.setProducts(products);
        return categoryDto;
    }
    // 특정 카테고리의 정보와 해당 카테고리의 모든 하위 카테고리 및 상품 가져오기
    public CategoryDto getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));
        return buildCategoryDtoWithSubcategories(category);
    }

    // 특정 카테고리에 속한 상품들만 가져오기
    public List<ProductDto> getCategoryProducts(Long categoryId) {
        return productService.findByCategoryId(categoryId).stream()
                .map(productService.getProductMapper()::toDto)
                .collect(Collectors.toList());
    }
    // 주어진 부모 카테고리의 모든 하위 카테고리 가져오기
    public List<CategoryDto> getSubCategories(Long parentCategoryId) {
        List<Category> subCategories = categoryRepository.findByParentCategoryId(parentCategoryId);
        return subCategories.stream()
                .map(this::buildCategoryDtoWithSubcategories)
                .collect(Collectors.toList());
    }
    // 카테고리 삭제 (하위 카테고리를 선택적으로 함께 삭제)
    public void deleteCategory(Long categoryId, boolean deleteSubcategory) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));
        if (deleteSubcategory) {
            category.getSubCategories().forEach(subCategory -> deleteCategory(subCategory.getId(), true));
        }
        categoryRepository.deleteById(categoryId);
    }
    public Long updateCategory(Long categoryId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));
        categoryMapper.updateCategoryFromDto(categoryDto, category);
        category = categoryRepository.save(category);
        return category.getId();
    }
}
