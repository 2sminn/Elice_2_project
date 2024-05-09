package com.elice.kittyandpuppy.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostViewController {
    @GetMapping("/communities")
    public String communityForm() {
        return "community";
    }

    @GetMapping("/community")
    public String communityDetailForm() {
        return "community_detail";
    }

    @GetMapping("/community/create")
    public String communityCreateForm() {
        return "community_write";
    }

    @GetMapping("/community/update")
    public String communityUpdateForm() {
        return "community_update";
    }
}