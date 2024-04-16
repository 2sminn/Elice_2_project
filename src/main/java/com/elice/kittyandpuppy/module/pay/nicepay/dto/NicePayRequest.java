package com.elice.kittyandpuppy.module.pay.nicepay.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NicePayRequest {
    String authResultCode;
    String authResultMsg;
    String tid;
    String clientId;
    String orderId;
    String amount;
    String mallReserved;
    String authToken;
    String signature;
}
