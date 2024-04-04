package com.elice.kittyandpuppy.module.order.dto.orderItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderItemRequest {
    private Long productId;
    private int price;
    private int amount;
}
