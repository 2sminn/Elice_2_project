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
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class OrderItemApiController {

    private final OrderItemService orderItemService;
    private final ProductService productService;

    @PostMapping("/orderItem")
    public ResponseEntity<Long> createOrderDetail(@RequestBody OrderItemRequest request) {
        Product product = productService.getProductById(request.getProductId());
        OrderItem createdOrderItem = orderItemService.create(product, request.getAmount());

        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderItem.getId());
    }

    @PostMapping("/orderItems")
    public ResponseEntity<List<Long>> createOrderDetail(@RequestBody List<OrderItemRequest> requests) {
        List<Long> orderItemIds = new ArrayList<>();

        for (OrderItemRequest request : requests) {
            Product product = productService.getProductById(request.getProductId());
            OrderItem createdOrderItem = orderItemService.create(product, request.getAmount());
            orderItemIds.add(createdOrderItem.getId());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(orderItemIds);
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

    @PostMapping("/orderItem/{productId}")
    public ResponseEntity<OrderItemResponse> createOrderItem(@PathVariable(value="productId") Long productId){
        OrderItem orderItem = orderItemService.create(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new OrderItemResponse(orderItem));
    }

    @GetMapping("/cart")
    public ResponseEntity<List<OrderItemResponse>> getCartById(@RequestParam("cartList") int[] orderId) {
        List<OrderItemResponse> orderItems = orderItemService.getCart(orderId);
        return new ResponseEntity<>(orderItems, HttpStatus.OK);
    }

    @PutMapping("/orderItem/{id}")
    public ResponseEntity<OrderItemResponse> updateOrderItem(@PathVariable(value = "id") Long id,
                                                             @RequestBody Map <String, Integer> requestData) {
        int amount = requestData.get("amount");
        OrderItem orderItem = orderItemService.update(id, amount);
        return ResponseEntity.status(HttpStatus.CREATED).body(new OrderItemResponse(orderItem));
    }
}