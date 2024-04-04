package com.elice.kittyandpuppy.module.order.dto.order;

import com.elice.kittyandpuppy.module.order.entity.Delivery;
import com.elice.kittyandpuppy.module.order.entity.Order;
import com.elice.kittyandpuppy.module.order.entity.OrderItem;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderResponse {
    private final Long id;
    private final List<OrderItem> orderItems;
    private final Delivery delivery;
    private final LocalDateTime date;
    private final String status;

    public OrderResponse(Order order) {
        this.id = order.getId();
        this.orderItems = order.getOrderItems();
        this.delivery = order.getDelivery();
        this.date = order.getOrderDate();
        this.status = order.getStatus().name();
    }
}
