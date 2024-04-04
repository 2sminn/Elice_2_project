package com.elice.kittyandpuppy.module.order.dto.delivery;

import com.elice.kittyandpuppy.module.order.entity.Delivery;
import lombok.Getter;

@Getter
public class DeliveryAddressResponse {

    private final String zipCode;
    private final String streetAddress;
    private final String detailAddress;
    private final String deliveryName;
    private final String deliveryPhone;

    public DeliveryAddressResponse(Delivery delivery) {
        this.zipCode = delivery.getAddress().getZipCode();
        this.streetAddress = delivery.getAddress().getStreet();
        this.detailAddress = delivery.getAddress().getDetail();
        this.deliveryName = getDeliveryName();
        this.deliveryPhone = getDeliveryPhone();
    }
}

// TODO: 위 코드와 아래 코드 중 어느것이 클라이언트에서 처리하기 편할지 고민 해봐야 함
//@Getter
//public class DeliveryResponse {
//
//    private final Address address;
//    private final String deliveryName;
//    private final String deliveryPhone;
//
//    public DeliveryResponse(Delivery delivery) {
//        this.address = delivery.getAddress();
//        this.deliveryName = getDeliveryName();
//        this.deliveryPhone = getDeliveryPhone();
//    }
//}