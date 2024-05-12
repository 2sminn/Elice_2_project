package com.elice.kittyandpuppy.module.product.service;

import com.elice.kittyandpuppy.module.product.dto.RequestProductDto;
import com.elice.kittyandpuppy.module.product.entity.Product;
import com.elice.kittyandpuppy.module.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findByCategoryId(Long categoryId) {
        return productRepository.findAllByCategoryId(categoryId);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findByCart(int[] cartList) {
        return Arrays.stream(cartList)
                .mapToObj(id -> productRepository.findById((long) id).orElse(null))
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toList());
    }

    public Product createProduct(RequestProductDto requestDto) {
        Product product = Product.fromDto(requestDto);
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        productRepository.delete(product);
    }

    public Product updateProduct(Long id, RequestProductDto updateRequestProductDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        updateProductFromRequestDto(updateRequestProductDto, product);
        return productRepository.save(product);
    }

    private void updateProductFromRequestDto(RequestProductDto dto, Product product) {
        product.setCategoryId(dto.getCategoryId());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setImageUrl(dto.getImageUrl());
        product.setDescription(dto.getDescription());
    }
}