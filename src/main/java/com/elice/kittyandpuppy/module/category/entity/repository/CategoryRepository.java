package com.elice.kittyandpuppy.module.category.entity.repository;

import com.elice.kittyandpuppy.module.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String categoryName);
    Optional<Category> findByBranchAndName(String categoryBranch, String categoryName);

    Boolean existsByBranchAndName(String categoryBranch, String categoryName);
}
