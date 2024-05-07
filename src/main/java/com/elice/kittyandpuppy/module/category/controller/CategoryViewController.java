package com.elice.kittyandpuppy.module.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class CategoryViewController {

    @GetMapping("api/categories")
    public String showCategories(){
        return "category";
    }
}
