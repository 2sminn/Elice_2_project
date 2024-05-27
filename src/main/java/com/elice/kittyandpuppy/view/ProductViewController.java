package com.elice.kittyandpuppy.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductViewController {
    @GetMapping("/product/view/{productId}")
    public String viewProductWithId(@PathVariable(value="productId") Long productId) {
        return "product";
    }

    @GetMapping("/product")
    public String viewProduct() {
        return "product";
    }

    @GetMapping("/product/write")
    public String productWrite() {
        return "/product/product_registration";
    }
}