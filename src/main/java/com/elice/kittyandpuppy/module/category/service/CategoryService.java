package com.elice.kittyandpuppy.module.category.service;

import com.elice.kittyandpuppy.module.category.entity.Category;
import com.elice.kittyandpuppy.module.category.dto.CategoryDto;
import com.elice.kittyandpuppy.module.category.dto.CategoryMapper;
import com.elice.kittyandpuppy.module.category.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
@RequiredArgsConstructor
@Setter
@Getter
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    public Long saveCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);

        if (categoryDto.getParentCategoryName() == null) {
            if (categoryRepository.existsByBranchAndName(categoryDto.getCategoryBranch(), categoryDto.getCategoryName())) {
                throw new RuntimeException("categoryBranch와 categoryName이 같을 수 없습니다.");
            }
            Category topCategory = categoryRepository.findByBranchAndName(categoryDto.getCategoryBranch(), "TOP")
                    .orElse(new Category());
            topCategory.setName("TOP");
            topCategory.setCode("TOP");
            topCategory.setBranch(categoryDto.getCategoryBranch());

            if (!categoryRepository.existsByBranchAndName(categoryDto.getCategoryBranch(), "TOP")) {
                categoryRepository.save(topCategory);
            }
            category.setParentCategory(topCategory);
        } else {
            String parentCategoryName = categoryDto.getParentCategoryName();
            Category parentCategory = categoryRepository.findByBranchAndName(categoryDto.getCategoryBranch(), parentCategoryName)
                    .orElseThrow(() -> new IllegalArgumentException("top카테고리 없음"));
            category.setParentCategory(parentCategory);
            parentCategory.getSubCategory().add(category);
        }
        return categoryRepository.save(category).getCategoryId();
    }

    public Map<String, CategoryDto> getCategoryByBranch(String branch) {
        Category category = categoryRepository.findByBranchAndName(branch, "TOP")
                .orElseThrow( () -> new IllegalArgumentException("찾는 부모카테고리가 없습니다"));

        CategoryDto categoryDto = new CategoryDto(category);

        Map<String, CategoryDto> data = new HashMap<>();
        data.put(categoryDto.getCategoryName(), categoryDto);
        return data;
    }

    public void deleteCategory(Long categoryId){
        Category category = findCategory(categoryId);

        if(category.getSubCategory().size() == 0){
            Category parentCategory = findCategory(category.getParentCategory().getCategoryId());
            if(!parentCategory.getName().equals("TOP")){
                parentCategory.getSubCategory().remove(category);
            }
            categoryRepository.deleteById(category.getCategoryId());
        }else{
            Category parentCategory = findCategory(category.getParentCategory().getCategoryId());
            if(!parentCategory.getName().equals("TOP")){
                parentCategory.getSubCategory().remove(category);
            }
            category.setName("삭제된 카테고리입니다.");
        }
        }

        public Long updateCategory (Long categoryId, CategoryDto categoryDto) {
            Category category = findCategory(categoryId);
            category.setName(categoryDto.getCategoryName());
            return category.getCategoryId();
        }

        private Category findCategory(Long categoryId) {
            return categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 ID에 대한 카테고리를 찾을 수 없습니다: " + categoryId));
        }

}
