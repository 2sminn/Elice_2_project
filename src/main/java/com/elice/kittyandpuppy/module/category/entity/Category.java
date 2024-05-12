package com.elice.kittyandpuppy.module.category.entity;

import com.elice.kittyandpuppy.module.category.dto.RequestCategoryDto;
import com.elice.kittyandpuppy.module.category.dto.ResponseCategoryDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "branch", nullable = false)
    private String branch;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
    private List<Category> subCategories = new ArrayList<>();

    @Builder
    public Category(Long id, String branch, String name, Category parentCategory, String code){
        this.id = id;
        this.branch = branch;
        this.name = name;
        this.code = code;
        this.parentCategory = parentCategory;
    }

    // Convert DTO to Entity
    public static Category fromDto(RequestCategoryDto dto) {
        Category category = Category.builder()
                .id(dto.getId())
                .branch(dto.getBranch())
                .name(dto.getName())
                .code(dto.getCode())
                .build();
        if (dto.getParentCategoryId() != null) {
            category.parentCategory = new Category();
            category.parentCategory.id = dto.getParentCategoryId();
        }
        return category;
    }

    // Convert Entity to DTO
    public ResponseCategoryDto toDto() {
        return ResponseCategoryDto.builder()
                .id(this.getId())
                .name(this.getName())
                .code(this.getCode())
                .branch(this.getBranch())
                .parentCategoryId(this.getParentCategory() != null ? this.getParentCategory().getId() : null)
                .subCategories(this.getSubCategories() != null ? this.getSubCategories().stream()
                        .map(Category::toDto).collect(Collectors.toList()) : null)
                .build();
    }
}