package com.elice.kittyandpuppy.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/order/fail")
    public String orderFail() {
        return "order_fail";
    }

}
