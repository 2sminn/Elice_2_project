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
@Table(name = "\"order\"")
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

    @Column(name = "order_date")
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
        validateSetDelivery();
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    protected void setStatus(OrderStatus status) {
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

        if (delivery != null) {
            order.setDelivery(delivery);
        }

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        order.setStatus(OrderStatus.CREATE);

        return order;
    }


    // 주문 상태 취소로 변경
    public void cancel() {
        validateStatusDelivery();
        validateStatusCancel();
        validateStatusComplete();

        this.status = OrderStatus.CANCEL;

        // 재고 복구 로직
        for (OrderItem orderItem : this.orderItems) {
            orderItem.cancel();
        }
    }

    // 주문 상태로 변경
    public void order() {
        this.orderDate = LocalDateTime.now();
        this.status = OrderStatus.ORDER;
    }

    // 주문 상태 배송중으로 변경
    public void delivery() {
        validateStatusDelivery();
        validateStatusCancel();
        validateStatusComplete();

        this.status = OrderStatus.DELIVERY;
    }


    // 주문 상태 완료로 변경
    public void complete() {
        validateStatusCancel();
        validateStatusComplete();

        this.status = OrderStatus.COMPLETE;
    }

    /**
     * 주문 상태 유효성 검사
     * @throws IllegalArgumentException 주문 상태 변경 실패<br>
     *                                  - CANCEL 상태인 경우: "취소된 상품입니다."
     */
    private void validateStatusCancel() {
        if (this.status == OrderStatus.CANCEL) {
            throw new IllegalStateException("취소된 상품입니다.");
        }
    }

    /**
     * 주문 상태 유효성 검사
     * @throws IllegalArgumentException 주문 상태 변경 실패<br>
     *                                  - DELIVERY 상태인 경우: "이미 배송중인 상품입니다."
     */
    private void validateStatusDelivery() {
        if (this.status == OrderStatus.DELIVERY) {
            throw new IllegalStateException("이미 배송중인 상품입니다.");
        }
    }

    /**
     * 주문 상태 유효성 검사
     * @throws IllegalArgumentException 주문 상태 변경 실패<br>
     *                                  - COMPLETE 상태인 경우: "이미 배송이 완료된 상품입니다."
     */
    private void validateStatusComplete() {
        if (this.status == OrderStatus.COMPLETE) {
            throw new IllegalStateException("이미 배송이 완료된 상품입니다.");
        }
    }

    /**
     * 배송지 변경시 주문 상태 유효성 검사
     * @throws IllegalArgumentException 배송지 변경 실패<br>
     *                                  - CREATE or ORDER 상태가 아닌 경우: "배송지 변경이 불가능합니다."
     */
    private void validateSetDelivery() {
        if (status == OrderStatus.DELIVERY || status == OrderStatus.COMPLETE || status == OrderStatus.CANCEL) {
            throw new IllegalArgumentException("배송지 변경이 불가능합니다.");
        }
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




