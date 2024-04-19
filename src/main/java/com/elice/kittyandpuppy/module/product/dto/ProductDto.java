package com.elice.kittyandpuppy.module.product.dto;


import com.elice.kittyandpuppy.module.product.entity.Product;
import lombok.*;

@Data // 표준적인 메서드를 자동으로 생성
@NoArgsConstructor // 기본 생성자 생성
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자를 생성
@Getter
@Setter

public class ProductDto {
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
