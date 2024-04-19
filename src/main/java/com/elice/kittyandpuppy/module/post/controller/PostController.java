package com.elice.kittyandpuppy.module.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class PostController {
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