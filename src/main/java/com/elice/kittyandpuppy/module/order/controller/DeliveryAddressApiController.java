package com.elice.kittyandpuppy.module.order.controller;

import com.elice.kittyandpuppy.module.order.dto.delivery.DeliveryAddressRequest;
import com.elice.kittyandpuppy.module.order.dto.delivery.DeliveryAddressResponse;
import com.elice.kittyandpuppy.module.order.entity.Delivery;
import com.elice.kittyandpuppy.module.order.service.DeliveryService;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/address/delivery")
public class DeliveryAddressApiController {

    private final DeliveryService deliveryService;

    @PostMapping("")
    public ResponseEntity<Long> addDelivery(@RequestBody DeliveryAddressRequest request) {
        Delivery delivery = deliveryService.create(
                request.getAddress(), request.getDeliveryName(), request.getDeliveryPhone()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(delivery.getId());
    }

    @GetMapping("{id}")
    public ResponseEntity<DeliveryAddressResponse> getDelivery(@PathVariable(value = "id") long id) {
        Delivery delivery = deliveryService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new DeliveryAddressResponse(delivery));
    }

    @PostMapping("{id}")
    public ResponseEntity<Long> updateDelivery(@PathVariable(value = "id") long id,
                                            @RequestBody DeliveryAddressRequest request) {
        deliveryService.update(
                id, request.getAddress(), request.getDeliveryName(), request.getDeliveryPhone()
        );

        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    // TODO: 배송지 정보 삭제가 필요할까? (because Order 삭제시 연쇄 삭제)
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable(value = "id") long id) {
        deliveryService.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
