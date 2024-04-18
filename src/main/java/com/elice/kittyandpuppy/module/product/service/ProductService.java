package com.elice.kittyandpuppy.module.product.service;

import com.elice.kittyandpuppy.module.product.dto.ProductMapper;
import com.elice.kittyandpuppy.module.product.entity.Product;
import com.elice.kittyandpuppy.module.product.dto.ProductDto;
import com.elice.kittyandpuppy.module.product.repository.ProductRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Setter
@Getter
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    @Getter
    private final ProductMapper productMapper;
    @Autowired // 생성자를 통한 의존성 주입 ProductService가 ProductRepository를 사용할 수 있게 된다.
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }


    @Transactional
    public ProductDto createProduct(ProductDto productDto) { // 상품을 생성하는 메서드 상품 DTO를 받아 새로운 상품 엔티티를 생성하고, 이를 저장한 후 반환.
        Product product = productMapper.toEntity(productDto);
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    public List<Product> findByCategoryId(Long categoryId) {
        return productRepository.findAllByCategoryId(categoryId);
    }

    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return productMapper.toDto(product);
    }

    // findAll : 모든 상품 조회
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    // deleteProduct : 해당 id 상품을 삭제하는 메서드
    @Transactional
    public void deleteProduct(Long id) {
        Product existingProduct = productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("죄송합니다. 상품을 찾을 수 없습니다."));
        productRepository.delete(existingProduct);
    }
    // findProductById : 주어진 id에 해당하는 상품을 조회하는 메서드
    public Product findProductById(Long id) {
        ProductDto productDto = productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("죄송합니다. 상품을 찾을 수 없습니다."));
        return productMapper.toEntity(productDto);
    }
    // 주어진 id에 해당하는 상품을 업데이트 하는 메서드
    public ProductDto updateProduct(Long id, ProductDto updateProductDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        productMapper.updateProductFromDto(updateProductDto, product);
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }
}