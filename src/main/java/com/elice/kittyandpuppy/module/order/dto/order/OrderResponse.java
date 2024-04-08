package com.elice.kittyandpuppy.module.order.dto.order;

import com.elice.kittyandpuppy.module.order.dto.delivery.DeliveryAddressResponse;
import com.elice.kittyandpuppy.module.order.dto.orderItem.OrderItemResponse;
import com.elice.kittyandpuppy.module.order.entity.Order;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderResponse {
    private final Long id;
    private final List<OrderItemResponse> orderItems;
    private final DeliveryAddressResponse deliveryAddress;
    private final LocalDateTime date;
    private final String status;

    public OrderResponse(Order order) {
        this.id = order.getId();
        this.orderItems = order.getOrderItems()
                .stream()
                .map(OrderItemResponse::new)
                .toList();
        this.deliveryAddress = new DeliveryAddressResponse(order.getDelivery());
        this.date = order.getOrderDate();
        this.status = order.getStatus().name();
    }
}
