package com.elice.kittyandpuppy.module.category.dto;

import com.elice.kittyandpuppy.module.category.entity.Category;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {CategoryMappingHelper.class})
public interface CategoryMapper {

    Category toEntity(CategoryDto categoryDTO);

    @InheritInverseConfiguration(name = "toEntity")
    CategoryDto toDto(Category category);

    void updateCategoryFromDto(CategoryDto categoryDto, @MappingTarget Category category);
}
