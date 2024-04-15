package com.elice.kittyandpuppy.module.pay.kakao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payment")
public class KakaoPayViewController {

    @GetMapping("/order/kakaopay")
    public String orderKakaoPay() {
        return "kakao_pay";
    }

    @GetMapping("/order/kakaopay/fail")
    public String orderKakaoPayFail() {
        return "kakao_pay_fail";
    }

}


