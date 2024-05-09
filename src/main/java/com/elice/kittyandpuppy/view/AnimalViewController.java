package com.elice.kittyandpuppy.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnimalViewController {

    @GetMapping("/animal")
    public String getAnimal() {
        return "animal";
    }

    @GetMapping("/animal/detail")
    public String getAnimalDetail() {
        return "animal_detail";
    }
}
