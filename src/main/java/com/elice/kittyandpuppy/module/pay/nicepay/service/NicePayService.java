package com.elice.kittyandpuppy.module.pay.nicepay.service;

import com.elice.kittyandpuppy.module.pay.nicepay.dto.NicePayApproveResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@RequiredArgsConstructor
@Service
public class NicePayService {

    @Value("${pay.nice-pay.secret-key}")
    private String secretKey;

    @Value("${pay.nice-pay.client-key}")
    private String clientKey;

    public NicePayApproveResponse getApprove(String tid, String amount) {

        HttpHeaders headers = new HttpHeaders();
        String auth = generateBasicAuthHeader(clientKey, secretKey);

        /** 요청 헤더 */
        headers.set("Content-type", "application/json");
        headers.set("Authorization", auth);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("amount", amount);

        HttpEntity<String> urlRequest = new HttpEntity<>(jsonObject.toString(), headers);

        RestTemplate rt = new RestTemplate();

        String url = "https://sandbox-api.nicepay.co.kr/v1/payments/" + tid;
        NicePayApproveResponse payReadyResDto = rt.postForObject(url, urlRequest, NicePayApproveResponse.class);

        return payReadyResDto;
    }

    private String generateBasicAuthHeader(String clientId, String clientSecret) {
        String credentials = clientId + ":" + clientSecret;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
        return "Basic " + encodedCredentials;
    }
}
