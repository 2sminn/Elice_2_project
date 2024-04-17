package com.elice.kittyandpuppy.global;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String getHome() {
        return "index";
    }

    @GetMapping("/animal")
    public String getAnimal() {
        return "animal";
    }

    @GetMapping("/animal/detail")
    public String getAnimalDetail() {
        return "animal_detail";
    }
}
