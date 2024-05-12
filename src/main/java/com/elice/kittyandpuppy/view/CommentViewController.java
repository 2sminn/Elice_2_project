package com.elice.kittyandpuppy.view;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommentViewController {
    @GetMapping("/comment")
    public String comment(){
        return "comment";
    }
}
