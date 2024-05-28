package com.elice.kittyandpuppy.module.category.dto;

import com.elice.kittyandpuppy.module.category.entity.Category;
import com.elice.kittyandpuppy.module.product.dto.ResponseProductDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ResponseCategoryDto {
    private Long id;
    private String name;
    private String code;
    private String branch;
    private Long parentCategoryId;
    private List<ResponseCategoryDto> subCategories;
    private List<ResponseProductDto> products;

    // Convert Entity to DTO
    public static ResponseCategoryDto fromEntity(Category entity) {
        if (entity == null) return null;

        return ResponseCategoryDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .code(entity.getCode())
                .branch(entity.getBranch())
                .parentCategoryId(entity.getParentCategory() != null ? entity.getParentCategory().getId() : null)
                .subCategories(entity.getSubCategories() != null ? entity.getSubCategories().stream()
                        .map(ResponseCategoryDto::fromEntity).collect(Collectors.toList()) : null)
                .products(entity.getProducts() != null ? entity.getProducts().stream()
                        .map(ResponseProductDto::fromEntity).collect(Collectors.toList()) : null)
                .build();
    }
}