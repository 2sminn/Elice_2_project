package com.elice.kittyandpuppy.module.order.dto.orderItem;

import com.elice.kittyandpuppy.module.order.entity.OrderItem;
import lombok.Getter;

@Getter
public class OrderItemResponse {

    private final String imageUrl;
    private final String name;
    private final int amount;
    private final int price;
    private final int totalPrice;

    public OrderItemResponse(OrderItem orderItem) {
        this.imageUrl = orderItem.getProduct().getImageUrl();
        this.name = orderItem.getProduct().getName();
        this.amount = orderItem.getProductAmount();
        this.price = orderItem.getProductPrice();
        this.totalPrice = orderItem.getTotalPrice();
    }
}
