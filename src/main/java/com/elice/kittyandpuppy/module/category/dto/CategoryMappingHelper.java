package com.elice.kittyandpuppy.module.category.dto;

import com.elice.kittyandpuppy.module.category.entity.Category;
import com.elice.kittyandpuppy.module.category.repository.CategoryRepository;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryMappingHelper {
    private final CategoryRepository categoryRepository;
    @Autowired
    public CategoryMappingHelper(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Named("idToCategory")
    public Category map(Long id) {
        if (id == null) {
            return null;
        }
        return categoryRepository.findById(id).orElse(null);
    }
}
