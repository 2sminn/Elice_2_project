package com.elice.kittyandpuppy.module.product.controller;

import com.elice.kittyandpuppy.module.product.dto.ProductDto;
import com.elice.kittyandpuppy.module.product.entity.Product;
import com.elice.kittyandpuppy.module.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    // new 상품 생성
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto createProduct = productService.createProduct(productDto);
        return new ResponseEntity<>(createProduct, HttpStatus.CREATED);
    }
    // all 상품 조회
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<Product> products = productService.findAll();
        List<ProductDto> productDtos = products.stream()
                .map(productService.getProductMapper()::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }
    // 특정 id 상품 조회
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable(value="productId") Long productId) {
        ProductDto product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(product);

    }
    // 특정 id 상품 수정
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto updateProductDto) {
        ProductDto updateProduct = productService.updateProduct(id, updateProductDto);
        return new ResponseEntity<>(updateProduct, HttpStatus.OK);
    }
    // 특정 id 상품 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}