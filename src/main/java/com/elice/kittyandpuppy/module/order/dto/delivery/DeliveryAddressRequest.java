package com.elice.kittyandpuppy.module.order.dto.delivery;

import com.elice.kittyandpuppy.global.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DeliveryAddressRequest {
    private Address address;
    private String deliveryName;
    private String deliveryPhone;
}
