package com.elice.kittyandpuppy.module.order.controller;

import com.elice.kittyandpuppy.module.order.dto.orderItem.OrderItemRequest;
import com.elice.kittyandpuppy.module.order.dto.orderItem.OrderItemResponse;
import com.elice.kittyandpuppy.module.order.entity.OrderItem;
import com.elice.kittyandpuppy.module.order.service.OrderItemService;
import com.elice.kittyandpuppy.module.product.entity.Product;
import com.elice.kittyandpuppy.module.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class OrderItemApiController {

    private final OrderItemService orderItemService;
    private final ProductService productService;

    @PostMapping("/orderItem")
    public ResponseEntity<Long> createOrderDetail(@RequestBody OrderItemRequest request) {
        Product product = productService.findProductById(request.getProductId());
        OrderItem createdOrderItem = orderItemService.create(product, request.getAmount());

        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderItem.getId());
    }

    @PostMapping("/orderItems")
    public ResponseEntity<List<Long>> createOrderDetail(@RequestBody List<OrderItemRequest> requests) {

        List<Long> orderItemIds = new ArrayList<>();

        for (OrderItemRequest request : requests) {
            Product product = productService.findProductById(request.getProductId());;
            OrderItem createdOrderItem = orderItemService.create(product, request.getAmount());
            orderItemIds.add(createdOrderItem.getId());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(orderItemIds);
    }

    //orderItem 수량 조절을 위한 수정 메소드 추가
    //TODO 본인스타일대로 수정하실거면 수정하십쇼~!~!
    @PutMapping("/orderItem/{id}") ResponseEntity<OrderItemResponse> updateOrderItem(@PathVariable(value = "id") long id,
                                                                                     @RequestBody long amount){
        OrderItem orderItem = orderItemService.update(id, amount);
        return ResponseEntity.status(HttpStatus.CREATED).body(new OrderItemResponse(orderItem));
    }

    @GetMapping("/orderItem/{id}")
    public ResponseEntity<OrderItemResponse> getOrderDetail(@PathVariable(value = "id") long id) {
        OrderItem orderItem = orderItemService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new OrderItemResponse(orderItem));
    }

    @DeleteMapping("/orderItem/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable(value = "id") long id) {
        orderItemService.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
