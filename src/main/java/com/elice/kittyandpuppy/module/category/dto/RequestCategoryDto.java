package com.elice.kittyandpuppy.module.category.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RequestCategoryDto {
    private Long id;
    private String name;
    private String code;
    private String branch;
    private Long parentCategoryId;
    private List<RequestCategoryDto> subCategories;  // 계층적 데이터 입력을 위한 중첩 구조
}