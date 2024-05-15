package com.elice.kittyandpuppy.module.cart.repository;


import com.elice.kittyandpuppy.module.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByMemberId(Long MemberId);
}
