package com.elice.kittyandpuppy.module.order.repository;

import com.elice.kittyandpuppy.global.Address;
import com.elice.kittyandpuppy.module.order.entity.Delivery;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DeliveryRepositoryTest {

    @Autowired
    DeliveryRepository deliveryRepository;

    private Address address;
    private Delivery delivery;
    private String receiverName;
    private String receiverPhone;

    @BeforeEach
    void cleanup() {
        deliveryRepository.deleteAll();
    }

    @BeforeEach
    void setup() {
        String zipCode = "12345";
        String street = "서울로 123";
        String detail = "상세주소";

        address = new Address(zipCode, street, detail);

        receiverName = "테스터";
        receiverPhone = "010-0000-0000";

        delivery = Delivery.createDelivery(address, receiverName, receiverPhone);
    }

    @DisplayName("배송지 저장 및 불러오기")
    @Test
    void deliverySaveAndLoadTest() {

        // given
        // setup 사용

        // when
        deliveryRepository.save(delivery);

        // then
        List<Delivery> deliveries = deliveryRepository.findAll();
        Delivery savedDelivery = deliveries.get(0);

        assertDeliveryEquals(savedDelivery, address, receiverName, receiverPhone);
    }

    @DisplayName("배송지 수정 및 불러오기")
    @Transactional
    @Test
    void deliveryUpdateAndLoadTest() {

        //given
        deliveryRepository.save(delivery);

        String updatedZipCode = "23456";
        String updatedStreet = "서울로 234";
        String updatedDetail = "수정주소";

        Address updatedAddress = new Address(updatedZipCode, updatedStreet, updatedDetail);

        String updatedReceiverName = "뉴테스터";
        String updatedReceiverPhone = "010-1111-2222";


        // when
        delivery.update(updatedAddress, updatedReceiverName, updatedReceiverPhone);

        // then
        List<Delivery> deliveries = deliveryRepository.findAll();
        Delivery savedDelivery = deliveries.get(0);

        assertDeliveryEquals(savedDelivery, updatedAddress, updatedReceiverName, updatedReceiverPhone);
    }

    @DisplayName("배송지 삭제")
    @Test
    void deliveryDeleteTest() {

        // given
        Delivery savedDelivery = deliveryRepository.save(delivery);

        // when
        deliveryRepository.deleteById(savedDelivery.getId());

        // then
        List<Delivery> deliveries = deliveryRepository.findAll();
        assertTrue(deliveries.isEmpty());
    }

    private void assertDeliveryEquals(Delivery delivery, Address address, String receiverName, String receiverPhone) {
        assertEquals(delivery.getAddress().getZipCode(), address.getZipCode());
        assertEquals(delivery.getAddress().getStreet(), address.getStreet());
        assertEquals(delivery.getAddress().getDetail(), address.getDetail());

        assertEquals(delivery.getReceiverName(), receiverName);
        assertEquals(delivery.getReceiverPhone(), receiverPhone);
    }
}
