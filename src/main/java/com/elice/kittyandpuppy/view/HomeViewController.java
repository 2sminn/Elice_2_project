package com.elice.kittyandpuppy.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeViewController {

    @GetMapping("/")
    public String getHome() {
        return "index";
    }
}
