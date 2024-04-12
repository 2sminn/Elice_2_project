package com.elice.kittyandpuppy.module.category.repository;

import com.elice.kittyandpuppy.module.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
    Optional<Category> findByBranchAndName(String branch, String name);

    Boolean existsByBranchAndName(String branch, String name);
}
