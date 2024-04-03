package com.elice.kittyandpuppy.module.product.service;

import com.elice.kittyandpuppy.module.product.entity.Product;
import com.elice.kittyandpuppy.module.product.dto.ProductDto;
import com.elice.kittyandpuppy.module.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;


    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(ProductDto productDto) {
        Product product = new Product(
            productDto.getCategory_id(),
            productDto.getProduct_name(),
            productDto.getProduct_price(),
            productDto.getProduct_stock(),
            productDto.getProduct_image_url(),
            productDto.getProduct_desc(),
            productDto.getCreated_at(),
            productDto.getModified_at()
        );
        return productRepository.save(product);
    }


    public List<Product> findAll() { return productRepository.findAll(); }


    @Transactional
    public void deleteProduct(Long id) {
        Product existingProduct = productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        productRepository.delete(existingProduct);

    }

    public Product findProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    }

    public Product updateProduct(Long id, ProductDto updateProductDto) {
        Product existingProduct = productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        existingProduct.setCategory_id(updateProductDto.getCategory_id());
        existingProduct.setProduct_name(updateProductDto.getProduct_name());
        existingProduct.setProduct_price(updateProductDto.getProduct_price());
        existingProduct.setProduct_stock(updateProductDto.getProduct_stock());
        existingProduct.setProduct_image_url(updateProductDto.getProduct_image_url());
        existingProduct.setProduct_desc(updateProductDto.getProduct_desc());
        existingProduct.setCreated_at(updateProductDto.getCreated_at());
        existingProduct.setModified_at(updateProductDto.getModified_at());
        return productRepository.save(existingProduct);


    }





}
