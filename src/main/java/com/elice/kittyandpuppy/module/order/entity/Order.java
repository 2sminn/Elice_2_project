package com.elice.kittyandpuppy.module.order.entity;

import com.elice.kittyandpuppy.global.Address;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

// TODO: Member 객체 연결하는 과정 필요
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    private Member member;

    @OneToMany(mappedBy = "order_detail")
    private List<OrderDetail> orderDetails;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @Column(name = "street_address", nullable = false)
    private String streetAddress;

    @Column(name = "detail_address", nullable = false)
    private String detailAddress;

    @Column(name = "receiver_name", nullable = false)
    private String receiverName;

    @Column(name = "receiver_phone", nullable = false)
    private String receiverPhone;

    @Builder
    public Order(/*Member member, */Address address, String receiverName, String receiverPhone) {
//        this.member = member;
        this.zipCode = address.getZipCode();
        this.streetAddress = address.getStreet();
        this.detailAddress = address.getDetail();
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
    }

    public void setOrderDetails(OrderDetail orderDetails) {
        this.orderDetails.add(orderDetails);
    }
}




