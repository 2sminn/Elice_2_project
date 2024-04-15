package com.elice.kittyandpuppy.module.pay.kakao.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.json.JSONObject;
import org.springframework.util.LinkedMultiValueMap;

@Getter
@AllArgsConstructor
public class KakaoPayRequest {
    private String url;
    private JSONObject jsonObject;
}
