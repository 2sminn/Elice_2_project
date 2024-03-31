package com.elice.kittyandpuppy.module.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// TODO: Product 객체 연결하는 과정 필요

/**
 * OrderDetail 객체
 * <p>주문하는 상품의 상품 정보, 개수, 구매 시점의 상품 가격을 관리한다.<br>
 * 또한, 한 번의 주문에서 여러개의 상품을 선택할 경우를 고려하여, OrderDetail 객체는 Order 객체와 다대일 관계를 갖는다.</p>
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "order_detail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

//    @ManyToOne
//    @JoinColumn(name = "product_id")
//    private Product product;

    @Column(name = "product_amount")
    private int productAmount;

    @Column(name = "product_price")
    private int productPrice;

    @Builder
    public OrderDetail(/*Product product, */Order order, int productAmount, int productPrice) {
//        this.product = product;
        this.order = order;
        this.productAmount = productAmount;
        this.productPrice = productPrice;
    }

    public int getTotalPrice() {
        return this.productAmount * this.productPrice;
    }

}
