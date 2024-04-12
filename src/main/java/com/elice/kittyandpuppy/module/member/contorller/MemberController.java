package com.elice.kittyandpuppy.module.member.contorller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/member")
public class MemberController {
    @GetMapping("/create")
    public String joinForm(){
        return "join";
    }
    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }
}
