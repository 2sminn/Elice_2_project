package com.elice.kittyandpuppy.module.product.dto;

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
}