package com.elice.kittyandpuppy.module.category.entity.service;

import com.elice.kittyandpuppy.module.category.entity.Category;
import com.elice.kittyandpuppy.module.category.entity.dto.CategoryDto;
import com.elice.kittyandpuppy.module.category.entity.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Long saveCategory (CategoryDto categoryDto){
        Category category = categoryDto.toEntity();

        Category topCategory = categoryRepository.findByBranchAndName(categoryDto.getCategoryBranch(), "TOP")
                .orElse(categoryRepository.save(Category.builder()
                                .categoryName("TOP")
                                .categoryCode("TOP")
                                .categoryBranch(categoryDto.getCategoryBranch())
                                .build()
                );
    }

}
