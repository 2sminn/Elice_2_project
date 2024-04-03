package com.elice.kittyandpuppy.module.order.dto.delivery;

import com.elice.kittyandpuppy.global.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DeliveryAddressRequest {
    private String zipCode;
    private String street;
    private String detail;
    private String deliveryName;
    private String deliveryPhone;

    public Address getAddress() {
        return new Address(zipCode, street, detail);
    }
}
