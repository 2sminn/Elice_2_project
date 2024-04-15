package com.elice.kittyandpuppy.module.order.entity;

import com.elice.kittyandpuppy.module.product.entity.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderItemTest {

    private static Product product;

    @BeforeAll
    public static void createProduct() {
        // TODO: product 엔티티가 만들어지면 수정되어야 함
        product = new Product();
    }

    @DisplayName("주문 상품 객체 생성 테스트")
    @Test
    public void addOrderItemTest() {

        // given
        int amount = 1;
        int price = 1_000;

        // when
        OrderItem orderItem = OrderItem.createOrderItem(product, amount, price);

        // then
        assertEquals(orderItem.getProduct(), product);
        assertEquals(orderItem.getProductAmount(), amount);
        assertEquals(orderItem.getProductPrice(), price);
    }

    @DisplayName("getTotalPrice(): 주문 상품 총 금액 테스트")
    @Test
    public void getTotalPriceTest() {

        // given
        int amount = 5;
        int price = 2_500;

        // when
        OrderItem orderItem = OrderItem.createOrderItem(product, amount, price);

        // then
        assertEquals(orderItem.getTotalPrice(), amount * price);
    }
}
