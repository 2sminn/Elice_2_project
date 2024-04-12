package com.elice.kittyandpuppy.module.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class OrderViewController {

    @GetMapping("/order")
    public String order() {
        return "order";
    }

    @GetMapping("/order/success")
    public String orderSuccess() {
        return "order_success";
    }

    @GetMapping("/footer.html")
    public String footer() {
        return"footer";
    }

    @GetMapping("/header.html")
    public String header() {
        return "header";
    }
}
