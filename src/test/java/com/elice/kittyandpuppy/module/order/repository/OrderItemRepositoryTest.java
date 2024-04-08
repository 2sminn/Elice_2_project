package com.elice.kittyandpuppy.module.order.repository;

import com.elice.kittyandpuppy.module.order.entity.OrderItem;
import com.elice.kittyandpuppy.module.product.entity.Product;
import com.elice.kittyandpuppy.module.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class OrderItemRepositoryTest {

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void cleanup() {
        orderItemRepository.deleteAll();
    }

    @BeforeEach
    void setup() {
        // TODO: 추후, new Product() 수정해야 함
        productRepository.deleteAll();
        productRepository.save(new Product());
    }

    @DisplayName("주문 상품 저장 및 불러오기")
    @Test
    void OrderItemUpdateAndLoadTest() {

        // given
        Product product = productRepository.findAll().get(0);
        int amount = 2;
        int price = 4_200;

        // when
        OrderItem orderItem = OrderItem.createOrderItem(product, amount, price);
        orderItemRepository.save(orderItem);

        // then
        List<OrderItem> savedOrderItems = orderItemRepository.findAll();
        OrderItem savedOrderItem = savedOrderItems.get(0);

        assertEquals(savedOrderItem.getProduct().getId(), product.getId());
        assertEquals(savedOrderItem.getProductAmount(), amount);
        assertEquals(savedOrderItem.getProductPrice(), price);
    }

    // TODO: 주문 상품을 삭제하더라도 상품은 삭제되면 안되는데 마음처럼 안됨
//    @DisplayName("주문 상품 삭제")
//    @Test
//    void OrderItemDeleteTest() {
//
//        // given
//        Product product = productRepository.findAll().get(0);
//        int amount = 2;
//        int price = 4_200;
//
//        OrderItem orderItem = OrderItem.createOrderItem(product, amount, price);
//        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
//
//        // when
//        orderItemRepository.deleteById(savedOrderItem.getId());
//
//        // then
//        List<OrderItem> savedOrderItems = orderItemRepository.findAll();
//        assertTrue(savedOrderItems.isEmpty());
//
//        Product mappedProduct = productRepository.findAll().get(0);
//        assertEquals(mappedProduct.getId(), 1L);
//    }
}
