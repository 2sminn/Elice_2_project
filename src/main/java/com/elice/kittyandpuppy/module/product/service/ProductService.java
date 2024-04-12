package com.elice.kittyandpuppy.module.product.service;

import com.elice.kittyandpuppy.module.product.entity.Product;
import com.elice.kittyandpuppy.module.product.dto.ProductDto;
import com.elice.kittyandpuppy.module.product.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;


    @Autowired // 생성자를 통한 의존성 주입 ProductService가 ProductRepository를 사용할 수 있게 된다.
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Transactional
    public Product createProduct(ProductDto productDto) { // 상품을 생성하는 메서드 상품 DTO를 받아 새로운 상품 엔티티를 생성하고, 이를 저장한 후 반환.
        Product product = new Product(
            productDto.getCategory_id(),
            productDto.getName(),
            productDto.getPrice(),
            productDto.getStock(),
            productDto.getImageUrl(),
            productDto.getDesc(),
            productDto.getCreated_at(),
            productDto.getModified_at()
        );
        return productRepository.save(product);
    }

    // findAll : 모든 상품 조회
    public List<Product> findAll() { return productRepository.findAll(); }


    // deleteProduct : 해당 id 상품을 삭제하는 메서드
    @Transactional
    public void deleteProduct(Long id) {
        Product existingProduct = productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("죄송합니다. 상품을 찾을 수 없습니다."));
        productRepository.delete(existingProduct);

    }

    // findProductById : 주어진 id에 해당하는 상품을 조회하는 메서드
    public Product findProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("죄송합니다. 상품을 찾을 수 없습니다."));
    }

    // 주어진 id에 해당하는 상품을 업데이트 하는 메서드
    public Product updateProduct(Long id, ProductDto updateProductDto) {
        Product existingProduct = productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("죄송합니다. 상품을 찾을 수 없습니다."));
        existingProduct.setCategory_id(updateProductDto.getCategory_id());
        existingProduct.setName(updateProductDto.getName());
        existingProduct.setPrice(updateProductDto.getPrice());
        existingProduct.setStock(updateProductDto.getStock());
        existingProduct.setImageUrl(updateProductDto.getImageUrl());
        existingProduct.setDesc(updateProductDto.getDesc());
        existingProduct.setCreated_at(updateProductDto.getCreated_at());
        existingProduct.setModified_at(updateProductDto.getModified_at());
        return productRepository.save(existingProduct);


    }





}
