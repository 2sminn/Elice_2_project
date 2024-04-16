package com.elice.kittyandpuppy.module.pay.kakao.dto;

import lombok.Getter;

@Getter
public class KakaoPayApproveResponse {
    private KakaoPayAmount amount; // 결제 금액 정보
    private String item_name; // 상품명
    private String created_at; // 결제 요청 시간
    private String approved_at; // 결제 승인 시간
}
