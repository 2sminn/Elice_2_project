package com.elice.kittyandpuppy.module.category.dto;

import com.elice.kittyandpuppy.module.category.entity.Category;
import com.elice.kittyandpuppy.module.product.dto.ProductDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
    private Long categoryId;
    private String name;
    private String code;
    private String branch;
    private Long parentCategoryId;
    private List<CategoryDto> subCategories;

    // 상품 목록 설정 메소드
    @Setter
    private List<ProductDto> products;

    public CategoryDto(Category entity){
        this.categoryId = entity.getId();
        this.name = entity.getName();
        this.code = entity.getCode();
        this.branch = entity.getBranch();
        this.parentCategoryId = entity.getParentCategory() != null ? entity.getParentCategory().getId() : null;
        this.subCategories = entity.getSubCategory().stream().map(CategoryDto::new).collect(Collectors.toList());
    }
}