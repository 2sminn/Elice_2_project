package com.elice.kittyandpuppy.module.order.controller;

import com.elice.kittyandpuppy.module.order.dto.orderItem.OrderItemRequest;
import com.elice.kittyandpuppy.module.order.dto.orderItem.OrderItemResponse;
import com.elice.kittyandpuppy.module.order.entity.OrderItem;
import com.elice.kittyandpuppy.module.order.service.OrderItemService;
import com.elice.kittyandpuppy.module.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

// TODO: productService 구현되면 주석처리한 부분 수정해야 함
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class OrderItemApiController {

    private final OrderItemService orderItemService;
//    private final ProductService productService;

    @PostMapping("/orderItem")
    public ResponseEntity<Long> createOrderDetail(@RequestBody OrderItemRequest request) {
        Product product = /*productService.findById(request.getProductId());*/ new Product();
        OrderItem createdOrderItem = orderItemService.create(product, request.getAmount());

        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderItem.getId());
    }

    @PostMapping("/orderItems")
    public ResponseEntity<List<Long>> createOrderDetail(@RequestBody List<OrderItemRequest> requests) {

        List<Long> orderItemIds = new ArrayList<>();

        for (OrderItemRequest request : requests) {
            Product product = /*productService.findById(request.getProductId());*/ new Product();
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

    // TODO: OrderItem 객체를 수정하는 로직은 필요할 것 같지 않다고 생각해서 제외

    @DeleteMapping("/orderItem/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable(value = "id") long id) {
        orderItemService.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
