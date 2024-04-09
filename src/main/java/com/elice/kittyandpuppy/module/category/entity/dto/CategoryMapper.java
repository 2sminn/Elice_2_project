package com.elice.kittyandpuppy.module.category.entity.dto;

import com.elice.kittyandpuppy.module.category.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(target = "categoryId", ignore = true)
    Category toEntity(CategoryDto categoryDTO);
}
