package com.elice.kittyandpuppy.module.order.service;

import com.elice.kittyandpuppy.module.member.entity.Member;
import com.elice.kittyandpuppy.module.order.entity.Delivery;
import com.elice.kittyandpuppy.module.order.entity.Order;
import com.elice.kittyandpuppy.module.order.entity.OrderItem;
import com.elice.kittyandpuppy.module.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    /**
     * Order 객체를 생성하고, order 테이블에 저장한다.
     *
     * @param member
     * @param delivery
     * @param orderItems
     * @return 생성된 Order 객체
     */
    @Transactional
    public Order create(Member member, Delivery delivery, List<OrderItem> orderItems) {
        Order createdOrder = Order.createOrder(member, delivery, orderItems);

        return orderRepository.save(createdOrder);
    }

    /**
     * id 값으로 Order 객체를 조회한다.
     * @param id
     * @throws IllegalArgumentException Order 조회 실패<br>
     *         - "Not Found Order Info By: {id}"
     * @return 찾은 Order 객체
     */
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not Found Order Info By: " + id));
    }

    /**
     * Member id 값으로 연관된 모든 Order 객체를 조회한다.
     *
     * @param memberId
     * @return 찾은 Order List
     */
    public List<Order> findAllByMemberId(Long memberId) {
        return orderRepository.findAllByMember_Id(memberId);
    }

    /**
     * Order 객체를 삭제한다.<br>
     * 삭제시 Cascade 설정으로 인해 해당하는 OrderItem, Delivery 정보들도 자동으로 삭제된다.
     * @param id
     */
    @Transactional
    public void deleteById(long id) {
        // 해당하는 정보가 존재하는지 확인
        findById(id);

        orderRepository.deleteById(id);
    }

    /**
     * id 값에 해당하는 Order 객체의 상태값을 주문으로 변경한다.
     *
     * @param id
     */
    @Transactional
    public void order(Long id) {
        findById(id).order();
    }

    /**
     * id 값에 해당하는 Order 객체의 상태값을 취소로 변경한다.
     *
     * @param id
     */
    @Transactional
    public void cancel(Long id) {
        findById(id).cancel();
    }

    /**
     * id 값에 해당하는 Order 객체의 상태값을 배달로 변경한다.
     *
     * @param id
     */
    @Transactional
    public void delivery(Long id) {
        findById(id).delivery();
    }

    /**
     * id 값에 해당하는 Order 객체의 상태값을 완료로 변경한다.
     *
     * @param id
     */
    @Transactional
    public void complete(Long id) {
        findById(id).complete();
    }

    /**
     * id 값에 해당하는 Order 객체의 배송지 정보를 업데이트 한다.
     *
     * @param id
     * @param delivery
     */
    @Transactional
    public void updateDelivery(Long id, Delivery delivery) {
        Order order = findById(id);
        order.setDelivery(delivery);
    }
}

