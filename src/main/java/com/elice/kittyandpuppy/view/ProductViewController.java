package com.elice.kittyandpuppy.view;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductViewController {
    @GetMapping("/product")
    public String viewProduct(){
        return "product";
    }

    @GetMapping("/product/write")
    public String productWrite(){
        return "/product/product_registration";
    }
}
