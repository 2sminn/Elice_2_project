package com.elice.kittyandpuppy.module.product.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductViewController {
    @GetMapping("/product/{productId}")
    public String viewProduct(@PathVariable(value="productId")Long productId){
        return "product";
    }
}
