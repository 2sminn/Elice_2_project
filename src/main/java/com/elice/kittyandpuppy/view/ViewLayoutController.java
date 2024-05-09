package com.elice.kittyandpuppy.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewLayoutController {

    @GetMapping("/footer.html")
    public String footer() {
        return"footer";
    }

    @GetMapping("/header.html")
    public String header() {
        return "header";
    }

}
