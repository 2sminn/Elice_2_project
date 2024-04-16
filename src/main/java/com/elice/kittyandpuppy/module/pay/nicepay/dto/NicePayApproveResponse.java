package com.elice.kittyandpuppy.module.pay.nicepay.dto;

import lombok.Getter;

@Getter
public class NicePayApproveResponse {
    private String resultCode;
    private String orderId;
    private String paidAt;
}
