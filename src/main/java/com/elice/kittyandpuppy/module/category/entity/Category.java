package com.elice.kittyandpuppy.module.category.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Getter
@NoArgsConstructor
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

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
    private List<Category> subCategory = new ArrayList<>();

    @Builder
    public Category(Long categoryId, String categoryBranch, String categoryName, Category parentCategory, String categoryCode){
        this.categoryId = categoryId;
        this.name = categoryName;
        this.parentCategory = parentCategory;
        this.code = categoryCode;
        this.branch = categoryBranch;
    }

}
