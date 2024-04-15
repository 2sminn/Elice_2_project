package com.elice.kittyandpuppy.module.member.contorller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class MemberController {
    @GetMapping("/signup")
    public String joinForm(){
        return "join";
    }
    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }
    @GetMapping("/mypage")
    public String mypageForm(){
        return "mypage";
    }
}
