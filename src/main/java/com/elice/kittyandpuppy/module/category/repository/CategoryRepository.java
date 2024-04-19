package com.elice.kittyandpuppy.module.category.repository;

import com.elice.kittyandpuppy.module.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByBranch(String branch);

    List<Category> findByParentCategoryId(Long parentId);
}
