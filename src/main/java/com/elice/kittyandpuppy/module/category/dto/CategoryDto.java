package com.elice.kittyandpuppy.module.category.dto;

import com.elice.kittyandpuppy.module.category.entity.Category;
import com.elice.kittyandpuppy.module.product.dto.ProductDto;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;


@Data
@Builder
public class CategoryDto {
    private Long id;
    private String name;
    private String code;
    private String branch;
    private Long parentCategoryId;
    private List<CategoryDto> subCategories;
    // 상품 목록 설정 메소드
    private List<ProductDto> products;

    public static CategoryDto fromEntity(Category entity) {
        if (entity == null) return null;

        return CategoryDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .code(entity.getCode())
                .branch(entity.getBranch())
                .parentCategoryId(entity.getParentCategory() != null ? entity.getParentCategory().getId() : null)
                .subCategories(entity.getSubCategories().stream().map(CategoryDto::fromEntity).collect(Collectors.toList()))
                .build();
    }
}