package com.elice.kittyandpuppy.module.pay.kakao.dto;

import lombok.Getter;

@Getter
public class KakaoPayReadyResponse {
    private String tid; // 결제 고유 번호
    private String next_redirect_pc_url; // 결제 페이지
    private String created_at;
}
