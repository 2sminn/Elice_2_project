package com.elice.kittyandpuppy.module.pay.nicepay.controller;

import com.elice.kittyandpuppy.module.pay.nicepay.dto.NicePayApproveResponse;
import com.elice.kittyandpuppy.module.pay.nicepay.dto.NicePayRequest;
import com.elice.kittyandpuppy.module.pay.nicepay.service.NicePayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class NicePayApiController {

    private final NicePayService nicePayService;

    @RequestMapping("/nicepay")
    public String createNicePay(NicePayRequest request, Model model) {

        if (!"0000".equals(request.getAuthResultCode())) {
            return "redirect:/order/fail?orderId=" + request.getOrderId();
        }

        NicePayApproveResponse result = nicePayService.getApprove(request.getTid(), request.getAmount());

        return "redirect:/order/success?orderId=" + request.getOrderId();
    }
}
