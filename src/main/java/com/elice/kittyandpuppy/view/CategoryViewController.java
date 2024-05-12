package com.elice.kittyandpuppy.view;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoryViewController {

    // TODO: api/categories -> /categories
    // API URL에서 api라는 단어 제거해야 함
    @GetMapping("/categories")
    public String showCategories(){
        return "category";
    }
}
