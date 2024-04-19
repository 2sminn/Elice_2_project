package com.elice.kittyandpuppy.module.product.repository;

import com.elice.kittyandpuppy.module.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategoryId(Long categoryId);
}
