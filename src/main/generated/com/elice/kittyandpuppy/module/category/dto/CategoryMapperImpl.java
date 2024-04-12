package com.elice.kittyandpuppy.module.category.dto;

import com.elice.kittyandpuppy.module.category.entity.Category;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
<<<<<<< HEAD
    date = "2024-04-12T10:31:37+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
=======
    date = "2024-04-12T13:20:05+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
>>>>>>> c98af4efe7d19e5b2cb8004b8ffb1fd6276d6d02
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category toEntity(CategoryDto categoryDTO) {
        if ( categoryDTO == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.categoryBranch( categoryDTO.getCategoryBranch() );
        category.categoryName( categoryDTO.getCategoryName() );
        category.categoryCode( categoryDTO.getCategoryCode() );

        return category.build();
    }
}
