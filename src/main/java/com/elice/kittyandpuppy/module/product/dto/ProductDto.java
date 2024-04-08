package com.elice.kittyandpuppy.module.product.dto;


import com.elice.kittyandpuppy.module.product.entity.Product;
import lombok.*;

@Data // 표준적인 메서드를 자동으로 생성
@NoArgsConstructor // 기본 생성자 생성
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자를 생성


public class ProductDto {
    private Long id;
    private Long category_id;
    private String product_name;
    private int product_price;
    private int product_stock;
    private String product_image_url;
    private String product_desc;
    private String created_at;
    private String modified_at;


    // Dto - > product (DTO 상품 객체 - > 상품 엔티티로 변환)
    public static Product toEntity(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId()); // DTO 객체 id 값을 엔티티 객체에 설정
        product.setCategory_id(productDto.getCategory_id());
        product.setProduct_name(productDto.getProduct_name());
        product.setProduct_price(productDto.getProduct_price());
        product.setProduct_stock(productDto.getProduct_stock());
        product.setProduct_image_url(productDto.getProduct_image_url());
        product.setProduct_desc(productDto.getProduct_desc());
        product.setCreated_at(productDto.getCreated_at());
        product.setModified_at(productDto.getModified_at());

        return product;





    }

    // product - > Dto
    public static ProductDto fromDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setCategory_id(product.getCategory_id());
        productDto.setProduct_name(product.getProduct_name());
        productDto.setProduct_price(product.getProduct_price());
        productDto.setProduct_stock(product.getProduct_stock());
        productDto.setProduct_image_url(product.getProduct_image_url());
        productDto.setProduct_desc(product.getProduct_desc());
        productDto.setCreated_at(product.getCreated_at());
        productDto.setModified_at(product.getModified_at());

        return productDto;
    }



}
