package com.elice.kittyandpuppy.module.product.dto;

import com.elice.kittyandpuppy.module.product.entity.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseProductDto {
    private Long id;
    private Long categoryId;
    private String name;
    private int price;
    private int stock;
    private String imageUrl;
    private String description;
    private String createdAt;
    private String modifiedAt;

    public static ResponseProductDto fromEntity(Product entity) {
        if (entity == null) return null;

        return ResponseProductDto.builder()
                .id(entity.getId())
                .categoryId(entity.getCategoryId())
                .name(entity.getName())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .imageUrl(entity.getImageUrl())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .modifiedAt(entity.getModifiedAt())
                .build();
    }
}