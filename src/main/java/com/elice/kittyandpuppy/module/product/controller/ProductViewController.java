package com.elice.kittyandpuppy.module.product.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/product")
public class ProductViewController {
    @GetMapping
    public String viewProduct(){return "product";}
}
