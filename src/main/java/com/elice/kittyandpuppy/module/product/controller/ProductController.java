package com.elice.kittyandpuppy.module.product.controller;

import com.elice.kittyandpuppy.module.product.dto.RequestProductDto;
import com.elice.kittyandpuppy.module.product.dto.ResponseProductDto;
import com.elice.kittyandpuppy.module.product.entity.Product;
import com.elice.kittyandpuppy.module.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // 상품 생성
    @PostMapping
    public ResponseEntity<ResponseProductDto> createProduct(@RequestBody RequestProductDto requestProductDto) {
        Product product = productService.createProduct(requestProductDto);
        return new ResponseEntity<>(product.toResponseDto(), HttpStatus.CREATED);
    }

    // 모든 상품 조회
    @GetMapping
    public ResponseEntity<List<ResponseProductDto>> getAllProducts() {
        List<Product> products = productService.findAll();
        List<ResponseProductDto> productDtos = products.stream()
                .map(Product::toResponseDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    // 카트에 담긴 상품 전달
    @GetMapping("/cart")
    public ResponseEntity<List<ResponseProductDto>> getCartById(@RequestParam("cartList") int[] cartList) {
        List<Product> products = productService.findByCart(cartList);
        List<ResponseProductDto> productDtos = products.stream()
                .map(Product::toResponseDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    // 특정 id 상품 조회
    @GetMapping("/{productId}")
    public ResponseEntity<ResponseProductDto> getProductById(@PathVariable(value="productId") Long productId) {
        Product product = productService.getProductById(productId);
        return ResponseEntity.ok(product.toResponseDto());
    }

    // 특정 id 상품 수정
    @PutMapping("/{id}")
    public ResponseEntity<ResponseProductDto> updateProduct(@PathVariable Long id, @RequestBody RequestProductDto updateRequestProductDto) {
        Product updatedProduct = productService.updateProduct(id, updateRequestProductDto);
        return new ResponseEntity<>(updatedProduct.toResponseDto(), HttpStatus.OK);
    }

    // 특정 id 상품 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}