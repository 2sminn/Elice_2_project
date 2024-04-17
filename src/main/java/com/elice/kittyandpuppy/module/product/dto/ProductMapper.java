package com.elice.kittyandpuppy.module.product.dto;

import com.elice.kittyandpuppy.module.product.entity.Product;
import com.elice.kittyandpuppy.module.product.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "modifiedAt", source = "modifiedAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    ProductDto toDto(Product product);

    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "modifiedAt", source = "modifiedAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    Product toEntity(ProductDto productDto);

    void updateProductFromDto(ProductDto updateProductDto, @MappingTarget Product product);
}
