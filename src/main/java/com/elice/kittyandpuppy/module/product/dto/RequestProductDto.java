package com.elice.kittyandpuppy.module.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RequestProductDto {
    private Long id;
    private Long categoryId;
    private String name;
    private int price;
    private int stock;
    private String imageUrl;
    private String description;
}