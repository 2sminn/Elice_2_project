package com.elice.kittyandpuppy.module.product.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductViewController {
    @GetMapping("/product")
    public String viewProduct(){
        return "product";
    }
}
