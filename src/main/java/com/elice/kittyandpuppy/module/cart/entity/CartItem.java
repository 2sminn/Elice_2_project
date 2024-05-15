package com.elice.kittyandpuppy.module.cart.entity;

import com.elice.kittyandpuppy.module.product.entity.Product;
import jakarta.persistence.*;

@Entity
public class CartItem {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
}
