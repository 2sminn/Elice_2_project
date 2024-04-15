package com.elice.kittyandpuppy.module.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
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
    private Long category_id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "stock")
    private int stock;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "\"desc\"")
    private String desc;

    @Column(name = "created_at")
    private String created_at;

    @Column(name = "modified_at")
    private String modified_at;

    public Product(Long category_id, String name, int price, int stock, String imageUrl,
                   String desc, String created_at, String modified_at) {
        this.category_id = category_id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.desc = desc;
        this.created_at = created_at;
        this.modified_at = modified_at;
    }

}
