package com.elice.kittyandpuppy.module.order.repository;

import com.elice.kittyandpuppy.module.order.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
