package com.elice.kittyandpuppy.module.order.controller;

import com.elice.kittyandpuppy.module.member.entity.Member;
import com.elice.kittyandpuppy.module.member.service.MemberService;
import com.elice.kittyandpuppy.module.order.dto.order.OrderResponse;
import com.elice.kittyandpuppy.module.order.dto.order.OrderRequest;
import com.elice.kittyandpuppy.module.order.entity.Delivery;
import com.elice.kittyandpuppy.module.order.entity.Order;
import com.elice.kittyandpuppy.module.order.entity.OrderItem;
import com.elice.kittyandpuppy.module.order.service.DeliveryService;
import com.elice.kittyandpuppy.module.order.service.OrderItemService;
import com.elice.kittyandpuppy.module.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class OrderApiController {

    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final DeliveryService deliveryService;
    private final MemberService memberService;

    @PostMapping("/order")
    public ResponseEntity<Long> addOrder(@RequestBody OrderRequest request) {

        // TODO: memberService 에서 예외를 처리하는게 낫지 않나?
        Member member = memberService.findMemberById(request.getMemberId()).orElseThrow();

        Delivery delivery = deliveryService.findById(request.getDeliveryId());

        List<OrderItem> orderItems = new ArrayList<>();
        for (Long orderItemId : request.getOrderItemIds()) {
            orderItems.add(orderItemService.findById(orderItemId));
        }

        Order order = orderService.create(member, delivery, orderItems);

        return ResponseEntity.status(HttpStatus.CREATED).body(order.getId());
    }
    

    //Cart에서 수량 변경후 주문하기 버튼을 누르면 사용되는 메소드 -> 사용X
    @PutMapping("/order/{orderId}")
    @Deprecated
    public ResponseEntity<Long> updateOrder(@PathVariable(value="orderId") Long orderId,
                                            @RequestBody OrderRequest request) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (Long orderItemId : request.getOrderItemIds()) {
            orderItems.add(orderItemService.findById(orderItemId));
        }
        Order order = orderService.updateOrder(orderId, orderItems);
        return ResponseEntity.status(HttpStatus.CREATED).body(order.getId());
    }

    @GetMapping("/orders/{memberId}")
    public ResponseEntity<List<OrderResponse>> findOrders(@PathVariable(value="memberId") Long memberId) {
        List<OrderResponse> orderResponses = orderService.findAllByMemberId(memberId)
                .stream()
                .map(OrderResponse::new)
                .toList();

        return ResponseEntity.ok().body(orderResponses);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<OrderResponse> findOrder(@PathVariable(value = "id") Long id) {
        Order order = orderService.findById(id);

        return ResponseEntity.ok().body(new OrderResponse(order));
    }

    // 주문 상태 관리 로직
    @PutMapping("/order/{id}/status/order")
    public ResponseEntity<Void> updateStatusToOrder(@PathVariable(value = "id") Long id) {
        orderService.order(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/order/{id}/status/delivery")
    public ResponseEntity<Void> updateStatusToDelivery(@PathVariable(value = "id") Long id) {
        orderService.delivery(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/order/{id}/status/complete")
    public ResponseEntity<Void> updateStatusToComplete(@PathVariable(value = "id") Long id) {
        orderService.complete(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/order/{id}/status/cancel")
    public ResponseEntity<Void> updateStatusToCancel(@PathVariable(value = "id") Long id) {
        orderService.cancel(id);

        return ResponseEntity.ok().build();
    }

    // 배송지 변경
    @PutMapping("/order/{id}/delivery/{deliveryId}")
    public ResponseEntity<Void> updateDelivery(@PathVariable(value = "id") Long id,
                                               @PathVariable(value = "deliveryId") Long deliveryId) {
        Delivery delivery = deliveryService.findById(deliveryId);
        orderService.updateDelivery(id, delivery);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable(value = "id") Long id) {
        orderService.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
