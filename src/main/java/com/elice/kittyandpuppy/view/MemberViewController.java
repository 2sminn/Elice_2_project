package com.elice.kittyandpuppy.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberViewController {
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
