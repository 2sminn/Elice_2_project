package com.elice.kittyandpuppy.module.product.entity;

import com.elice.kittyandpuppy.module.product.dto.RequestProductDto;
import com.elice.kittyandpuppy.module.product.dto.ResponseProductDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // getter, setter 등 여러 메소드들을 자동으로 생성
@Entity // 클래스가 데이터베이스의 테이블과 매핑됨
@NoArgsConstructor // Lombok 파라미터가 없는 기본 생성자 생성
@AllArgsConstructor // 모든 필드를 파라미터로 받는 생성자 생성
@Table(name = "product") // 해당 엔티티가 매핑되는 DB 테이블의 이름 지정
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "stock")
    private int stock;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private String createdAt;
    @Column(name = "modified_at")
    private String modifiedAt;
    @Builder
    public Product(Long categoryId, String name, int price, int stock, String imageUrl,
                   String description, String createdAt, String modifiedAt) {
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.description = description;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    // DTO to Entity conversion
    public static Product fromDto(RequestProductDto dto) {
        return Product.builder()
                .categoryId(dto.getCategoryId())
                .name(dto.getName())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .imageUrl(dto.getImageUrl())
                .description(dto.getDescription())
                .build();
    }

    // Entity to DTO conversion
    public ResponseProductDto toResponseDto() {
        return ResponseProductDto.builder()
                .id(this.getId())
                .categoryId(this.getCategoryId())
                .name(this.getName())
                .price(this.getPrice())
                .stock(this.getStock())
                .imageUrl(this.getImageUrl())
                .description(this.getDescription())
                .createdAt(this.getCreatedAt())
                .modifiedAt(this.getModifiedAt())
                .build();
    }
}
