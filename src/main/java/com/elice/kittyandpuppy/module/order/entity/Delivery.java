package com.elice.kittyandpuppy.module.order.entity;

import com.elice.kittyandpuppy.global.Address;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Delivery 객체
 * <p>배송지 정보를 관리한다.<br>
 * 배송지 정보는 주문과 1대1 매칭되므로, Order 객체와 일대일 연관관계를 갖는다.</p>
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "delivery")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Column(name = "receiver_name", nullable = false)
    private String receiverName;

    @Column(name = "receiver_phone", nullable = false)
    private String receiverPhone;

    /**
     * Delivery 객체를 생성
     *
     * @param address
     * @param receiverName
     * @param receiverPhone
     * @return 생성된 Delivery 객체
     */
    public static Delivery createDelivery(Address address, String receiverName, String receiverPhone) {

        Delivery delivery = new Delivery();
        delivery.setAddress(address);
        delivery.setReceiverName(receiverName);
        delivery.setReceiverPhone(receiverPhone);

        return delivery;
    }

    /**
     * 배송지 정보 수정
     *
     * @param address
     * @param receiverName
     * @param receiverPhone
     * @return 수정된 Delivery 객체
     */
    public Delivery update(Address address, String receiverName, String receiverPhone) {
        this.address = address;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;

        return this;
    }


    // 연관관계 맵핑에 사용
    public void setOrder(Order order) {
        this.order = order;
    }

    // 무분별한 수정을 막기 위해 setter 접근제어자를 protected 설정
    protected void setAddress(Address address) {
        this.address = address;
    }

    protected void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    protected void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }
}
