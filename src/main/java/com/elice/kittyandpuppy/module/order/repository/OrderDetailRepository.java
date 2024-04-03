package com.elice.kittyandpuppy.module.order.repository;

import com.elice.kittyandpuppy.module.order.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
