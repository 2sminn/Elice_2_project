package com.elice.kittyandpuppy.module.order.service;

import com.elice.kittyandpuppy.global.Address;
import com.elice.kittyandpuppy.module.order.entity.Delivery;
import com.elice.kittyandpuppy.module.order.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    /**
     * Delivery 객체를 생성하고 deliver 테이블에 저장한다.
     *
     * @param address
     * @param deliveryName
     * @param deliveryPhone
     * @return 생성된 Delivery 객체
     */
    @Transactional
    public Delivery create(Address address, String deliveryName, String deliveryPhone) {
        return deliveryRepository.save(Delivery.createDelivery(address, deliveryName, deliveryPhone));
    }

    /**
     * id 값으로 Delivery 객체를 조회한다.
     *
     * @param  id
     * @throws IllegalArgumentException 배송지 정보 조회 실패<br>
     *         - "Not Found Delivery Info By: {id}"
     * @return 찾은 Delivery 객체
     */
    public Delivery findById(long id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not Found Delivery Info By: " + id));
    }

    /**
     * id 값에 해당하는 Delivery 객체의 값을 수정한다.
     *
     * @param id
     * @param address
     * @param deliveryName
     * @param deliveryPhone
     *
     * @return 찾은 Delivery 객체
     */
    @Transactional
    public Delivery update(long id, Address address, String deliveryName, String deliveryPhone) {
        Delivery delivery = findById(id);

        delivery.update(address, deliveryName, deliveryPhone);

        return delivery;
    }

    /**
     * id 값에 해당하는 Delivery 객체를 삭제한다.
     * <p>어차피, Order를 삭제하면 Cascade 설정으로 인해 Delivery도 자동으로 삭제된다.<br>
     * 정말 필요한 경우에만 사용해야한다.</p>
     *
     * @param id
     */
    @Transactional
    public void deleteById(long id) {
        // 해당하는 정보가 존재하는지 확인
        findById(id);

        deliveryRepository.deleteById(id);
    }
}
