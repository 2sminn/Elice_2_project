package com.elice.kittyandpuppy.module.order.entity;

import com.elice.kittyandpuppy.module.member.entity.Member;
import com.elice.kittyandpuppy.module.order.config.OrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Delivery delivery;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // 객체 연관 관계 맵핑 로직
    // TODO: Member 객체와 연결햐여 험
    public void setMember(Member member) {
        this.member = member;
//        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }


    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    // 객체 생성 로직
    /**
     * Order 객체를 생성
     *
     * @param member
     * @param delivery
     * @param orderItems
     * @return 저장된 Order 객체
     */
    public static Order createOrder(Member member, Delivery delivery, List<OrderItem> orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);

        for(OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    // 주문 상태 변경 로직
    /**
     * 주문 상태 취소로 변경
     * @throws IllegalArgumentException 주문 상태 변경 실패<br>
     *         - 상태가 배송중인 경우: "배송중인 상품은 취소가 불가능합니다." <br>
     *         - 상태가 배송완료인 경우: "배송이 완료된 상품은 취소가 불가능합니다."
     */
    public void cancel() {
        if(this.status == OrderStatus.DELIVERY) {
            throw new IllegalStateException("배송중인 상품은 취소가 불가능합니다.");
        }

        if(this.status == OrderStatus.COMPLETE) {
            throw new IllegalStateException("배숭이 완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        // 재고 복구 로직
        for(OrderItem orderItem : this.orderItems) {
            orderItem.cancel();
        }
    }

    /**
     * 주문 상태 주문으로 변경
     *
     * @throws IllegalArgumentException 주문 상태 변경 실패<br>
     *         - ORDER 이외의 상태인 경우: "이미 배송이 시작되었거나, 취소된 상품입니다."
     */
    public void delivery() {
        if(this.status != OrderStatus.ORDER) {
            throw new IllegalStateException("이미 배송이 시작되었거나, 취소된 상품입니다.");
        }

        this.setStatus(OrderStatus.DELIVERY);
    }

    /**
     * 주문 상태 완료로 변경
     *
     * @throws IllegalArgumentException 주문 상태 변경 실패<br>
     *         - COMPLETE 상태인 경우: "이미 배송이 완료된 상품입니다."
     */
    public void complete() {
        if(this.status == OrderStatus.COMPLETE) {
            throw new IllegalStateException("이미 배송이 완료된 상품입니다.");
        }

        this.setStatus(OrderStatus.COMPLETE);
    }

    // 비지니스 로직
    /**
     * 주문한 상품들의 총 금액을 계산한다.
     *
     * @return 주문한 상품의 총 금액
     */
    public int getTotalPrice() {
        int totalPrice = 0;

        for (OrderItem orderItem : this.orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }

        return totalPrice;
    }
}




