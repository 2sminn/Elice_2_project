package com.elice.kittyandpuppy.module.order.repository;

import com.elice.kittyandpuppy.module.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByMember_Id(Long memberId);
}