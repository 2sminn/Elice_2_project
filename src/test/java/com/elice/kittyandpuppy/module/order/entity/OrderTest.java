package com.elice.kittyandpuppy.module.order.entity;

import com.elice.kittyandpuppy.global.Address;
import com.elice.kittyandpuppy.module.member.entity.Member;
import com.elice.kittyandpuppy.module.order.config.OrderStatus;
import com.elice.kittyandpuppy.module.product.entity.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {

    private static Member member;
    private static List<OrderItem> orderItems;
    private static Delivery delivery;

    @BeforeAll
    public static void setup() {
        member = new Member("사용자", "test@gmail.com", "password");

        // TODO: product 엔티티가 만들어지면 수정 필요
        List<OrderItem> list = new ArrayList<>();
        list.add(OrderItem.createOrderItem(new Product(), 2, 2_000));
        list.add(OrderItem.createOrderItem(new Product(), 5, 1_500));
        list.add(OrderItem.createOrderItem(new Product(), 10, 1_100));
        orderItems = list;

        Address address = new Address("12345", "서울로 123", "상세주소");
        delivery = Delivery.createDelivery(address, "받는사람", "010-0000-0000");
    }

    @DisplayName("주문 객체 생성 테스트")
    @Test
    public void addOrderTest() {

        // when
        Order order = Order.createOrder(member, delivery, orderItems);

        // then
        assertEquals(order.getMember(), member);
        assertEquals(order.getDelivery(), delivery);
        assertEquals(order.getOrderItems(), orderItems);
        assertEquals(order.getStatus(), OrderStatus.ORDER);
    }

    // 주문 상태 취소 관련 테스트
    @DisplayName("주문 상태 취소 변경 테스트")
    @Test
    public void orderStatusCancelTest(){

        // given
        Order order = Order.createOrder(member, delivery, orderItems);

        // when
        order.cancel();

        // then
        assertEquals(order.getStatus(), OrderStatus.CANCEL);
    }

    @DisplayName("배송중인 주문을 주문 취소로 변경하는 실패 테스트")
    @Test
    public void orderStatusCancelFailTest1(){

        Order order = Order.createOrder(member, delivery, orderItems);
        order.delivery();

        assertThatThrownBy(() -> order.cancel())
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("배송 완료 주문을 취소로 변경하는 실패 테스트")
    @Test
    public void orderStatusCancelFailTest2(){

        Order order = Order.createOrder(member, delivery, orderItems);
        order.complete();

        assertThatThrownBy(() -> order.cancel())
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("이미 취소된 주문을 주문 취소로 변경하는 실패 테스트")
    @Test
    public void orderStatusCancelFailTest3(){

        Order order = Order.createOrder(member, delivery, orderItems);
        order.cancel();

        assertThatThrownBy(() -> order.cancel())
                .isInstanceOf(IllegalStateException.class);
    }

    // 주문 상태 배송중 관련 테스트
    @DisplayName("주문 상태 배송중으로 변경 테스트")
    @Test
    public void orderStatusDeliveryTest(){

        // given
        Order order = Order.createOrder(member, delivery, orderItems);

        // when
        order.delivery();

        // then
        assertEquals(order.getStatus(), OrderStatus.DELIVERY);
    }

    @DisplayName("이미 배송중인 주문을 배송중으로 변경하는 실패 태스트")
    @Test
    public void orderStatusDeliveryFailTest1(){

        Order order = Order.createOrder(member, delivery, orderItems);
        order.delivery();

        assertThatThrownBy(() -> order.delivery())
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("취소된 상품을 배송중으로 변경하는 실패 태스트")
    @Test
    public void orderStatusDeliveryFailTest2(){

        Order order = Order.createOrder(member, delivery, orderItems);
        order.cancel();

        assertThatThrownBy(() -> order.delivery())
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("배송 완료된 상품을 배송중으로 변경하는 실패 태스트")
    @Test
    public void orderStatusDeliveryFailTest3(){

        Order order = Order.createOrder(member, delivery, orderItems);
        order.cancel();

        assertThatThrownBy(() -> order.delivery())
                .isInstanceOf(IllegalStateException.class);
    }

    // 주문 상태 배송 완료 관련 테스트
    @DisplayName("주문 상태 배송 완료 변경 테스트")
    @Test
    public void orderStatusCompleteTest(){

        // given
        Order order = Order.createOrder(member, delivery, orderItems);
        order.delivery();

        // when
        order.complete();

        // then
        assertEquals(order.getStatus(), OrderStatus.COMPLETE);
    }

    @DisplayName("취소된 주문을 배송 완료로 변경하는 실패 테스트")
    @Test
    public void orderStatusCompleteFailTest1(){

        Order order = Order.createOrder(member, delivery, orderItems);
        order.cancel();

        assertThatThrownBy(() -> order.complete())
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("배송 완료된 주문을 배송 완료로 변경하는 실패 테스트")
    @Test
    public void orderStatusCompleteFailTest2(){

        Order order = Order.createOrder(member, delivery, orderItems);
        order.complete();

        assertThatThrownBy(() -> order.complete())
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("getTotalPrice(): 총 금액 테스트")
    @Test
    public void totalPriceTest() {

        Order order = Order.createOrder(member, delivery, orderItems);
        int totalPrice = orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();

        assertEquals(order.getTotalPrice(), totalPrice);
    }
}
