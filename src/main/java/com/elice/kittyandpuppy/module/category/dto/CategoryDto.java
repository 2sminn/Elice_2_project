package com.elice.kittyandpuppy.module.category.dto;

import com.elice.kittyandpuppy.module.category.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
    private Long categoryId;
    private String categoryName;
    private String categoryCode;
    private String categoryBranch;
    private String parentCategoryName;
    private Map<String, CategoryDto> childCategory;

    public CategoryDto(Category entity){

        this.categoryId = entity.getCategoryId();
        this.categoryName = entity.getCategoryName();
        this.categoryCode = entity.getCategoryCode();
        this.categoryBranch = entity.getCategoryBranch();

        if(entity.getParentCategory() == null){
            this.parentCategoryName = "부모카테고리";
        }else{
            this.parentCategoryName = entity.getParentCategory().getCategoryName();
        }
        this.childCategory = entity.getSubCategory() == null ? null :
                entity.getSubCategory().stream().collect(Collectors.toMap(Category::getCategoryCode, CategoryDto::new));
    }

    public Category toEntity(){
        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setCategoryName(categoryName);
        category.setCategoryCode(categoryCode);
        category.setCategoryBranch(categoryBranch);
        return category;
    }
}
