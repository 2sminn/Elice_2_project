package com.elice.kittyandpuppy.module.order.service;

import com.elice.kittyandpuppy.module.order.entity.OrderItem;
import com.elice.kittyandpuppy.module.order.repository.OrderItemRepository;
import com.elice.kittyandpuppy.module.product.entity.Product;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    /**
     * OrderItem 객체를 생성하고, order_Item 테이블에 저장한다.
     *
     * @param product
     * @param price
     * @param amount
     *
     * @return 생성된 OrderItem 객체
     */
    @Transactional
    public OrderItem create(Product product, int price, int amount) {
        return orderItemRepository.save(OrderItem.createOrderItem(product, amount, price));
    }

    /**
     * id 값으로 OrderItem 객체를 조회한다.
     *
     * @param  id
     * @throws IllegalArgumentException OrderItem 조회 실패<br>
     *         - "Not Found OrderItem Info By: {id}"
     * @return 찾은 OrderItem 객체
     */
    public OrderItem findById(long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not Found OrderItem Info By: " + id));
    }

    // TODO: OrderItem 객체를 수정하는 상황은 없을 것 같다고 생각해서 일단 제외

    /**
     * OrderItem 객체를 삭제한다.
     * <p>어차피, Order 객체를 삭제하면 Cascade 설정으로 인해 OrderItem 객체들도 자동으로 삭제된다.<br>
     * 정말 필요한 경우에만 사용해야한다.</p>
     *
     * @param id
     */
    public void deleteById(long id) {
        // 해당하는 정보가 존재하는지 확인
        findById(id);

        orderItemRepository.deleteById(id);
    }

}
