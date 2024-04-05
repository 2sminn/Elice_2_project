package com.elice.kittyandpuppy.module.category.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Category")
@Getter
@NoArgsConstructor
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(nullable = false)
    private String categoryBranch;

    private String categoryName;

    private String categoryCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
    private List<Category> subCategory = new ArrayList<>();

    @Builder
    public Category(Long categoryId, String categoryBranch, String categoryName, Category parentCategory, String categoryCode){
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.parentCategory = parentCategory;
        this.categoryCode = categoryCode;
        this.categoryBranch = categoryBranch;
    }

}
