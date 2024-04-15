package com.elice.kittyandpuppy.module.pay.kakao.dto;

import lombok.Getter;

@Getter
public class KakaoPayInfoRequest {
    private Long orderId;
    private String token;
}
