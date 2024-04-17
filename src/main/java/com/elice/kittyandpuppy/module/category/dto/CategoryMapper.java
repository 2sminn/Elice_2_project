package com.elice.kittyandpuppy.module.category.dto;

import com.elice.kittyandpuppy.module.category.entity.Category;
import com.elice.kittyandpuppy.module.category.repository.CategoryRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {CategoryMappingHelper.class})
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(target = "categoryId", ignore = true)
    @Mapping(source = "parentCategoryId", target = "parentCategory", qualifiedByName = "idToCategory")
    Category toEntity(CategoryDto categoryDTO);

    CategoryDto fromEntity(Category category);

    @Mapping(target = "subCategories", ignore = true)  // 재귀 구조를 막기 위해 하위 카테고리 매핑 무시
    CategoryDto toDto(Category category);

    void updateCategoryFromDto(CategoryDto categoryDto, @MappingTarget Category category);
}
