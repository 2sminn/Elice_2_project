package com.elice.kittyandpuppy.module.product.dto;


import com.elice.kittyandpuppy.module.product.entity.Product;
import lombok.*;

@Data // 표준적인 메서드를 자동으로 생성
@NoArgsConstructor // 기본 생성자 생성
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자를 생성


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


    // Dto - > product (DTO 상품 객체 - > 상품 엔티티로 변환)
    public static Product toEntity(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId()); // DTO 객체 id 값을 엔티티 객체에 설정
        product.setCategoryId(productDto.getCategoryId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());
        product.setImageUrl(productDto.getImageUrl());
        product.setDescription(productDto.getDescription());
        product.setCreatedAt(productDto.getCreatedAt());
        product.setModifiedAt(productDto.getModifiedAt());

        return product;





    }

    // product - > Dto
    public static ProductDto fromDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setCategoryId(product.getCategoryId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setStock(product.getStock());
        productDto.setImageUrl(product.getImageUrl());
        productDto.setDescription(product.getDescription());
        productDto.setCreatedAt(product.getCreatedAt());
        productDto.setModifiedAt(product.getModifiedAt());

        return productDto;
    }



}
