package com.elice.kittyandpuppy.module.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "category_id")
    private Long category_id;

    @Column(name = "product_name")
    private String product_name;

    @Column(name = "product_price")
    private int product_price;

    @Column(name = "product_stock")
    private int product_stock;

    @Column(name = "product_image_url")
    private String product_image_url;

    @Column(name = "product_desc")
    private String product_desc;

    @Column(name = "created_at")
    private String created_at;

    @Column(name = "modified_at")
    private String modified_at;

    public Product(Long category_id, String product_name, int product_price, int product_stock, String product_image_url,
                   String product_desc, String created_at, String modified_at) {
        this.category_id = category_id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_stock = product_stock;
        this.product_image_url = product_image_url;
        this.product_desc = product_desc;
        this.created_at = created_at;
        this.modified_at = modified_at;
    }

}

