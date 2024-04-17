package com.elice.kittyandpuppy.module.order.entity;

import com.elice.kittyandpuppy.module.product.dto.ProductDto;
import com.elice.kittyandpuppy.module.product.entity.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// TODO: Product 객체 연결하는 과정 필요

/**
 * OrderItem 객체
 * <p>주문하는 상품의 상품 정보, 개수, 구매 시점의 상품 가격을 관리한다.<br>
 * 또한, 한 번의 주문에서 여러개의 상품을 선택할 경우를 고려하여, OrderItem 객체는 Order 객체와 다대일 관계를 갖는다.</p>
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "product_amount")
    private int productAmount;

    @Column(name = "product_price")
    private int productPrice;

    public static OrderItem createOrderItem(Product product, int productAmount, int productPrice) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setProductAmount(productAmount);
        orderItem.setProductPrice(productPrice);

        // TODO: 상품 재고 감소 로직 작성
        return orderItem;
    }

    // 연관관계 맵핑에 사용
    public void setOrder(Order order) {
        this.order = order;
    }
    /**
     * 주문이 취소된 경우 재고를 복구한다.
     */
    public void cancel() {
        // TODO: 재고 복구하는 로직 작성
//        this.product.addStock(this.productAmount);
    }

    /**
     * 주문 상품의 총 금액을 계산한다.
     *
     * @return 주문 상품의 총 금액
     */
    public int getTotalPrice() {
        return this.productAmount * this.productPrice;
    }

    // 외부에서 데이터 변경이 없도록 private 접근제어자로 setter 작성
    private void setProduct(Product product) {
        this.product = product;
    }


    //Cart 수량조절을 위해 private에서 public으로 변경
    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    private void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }
}
