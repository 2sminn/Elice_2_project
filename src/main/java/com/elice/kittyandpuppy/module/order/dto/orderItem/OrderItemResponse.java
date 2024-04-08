package com.elice.kittyandpuppy.module.order.dto.orderItem;

import com.elice.kittyandpuppy.module.order.entity.OrderItem;
import lombok.Getter;

// TODO: 상품 정보들도 전부 출력해야 하나? -> product 상세정보도 같이 반환하게 변경할듯
@Getter
public class OrderItemResponse {

    private final Long productId;
    private final int productAmount;
    private final int productPrice;
    private final int totalPrice;

    // TODO: 상품 도메인 어느정도 진행되면 수정해야 함
    public OrderItemResponse(OrderItem orderItem) {
        this.productId = /*orderItem.getProduct().getId()*/ 1L;
        this.productAmount = orderItem.getProductAmount();
        this.productPrice = orderItem.getProductPrice();
        this.totalPrice = orderItem.getTotalPrice();
    }
}
