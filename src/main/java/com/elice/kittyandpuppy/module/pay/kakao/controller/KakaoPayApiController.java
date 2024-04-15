package com.elice.kittyandpuppy.module.pay.kakao.controller;

import com.elice.kittyandpuppy.global.jwt.TokenProvider;
import com.elice.kittyandpuppy.module.order.entity.Order;
import com.elice.kittyandpuppy.module.order.service.OrderService;
import com.elice.kittyandpuppy.module.pay.kakao.dto.KakaoPayApproveResponse;
import com.elice.kittyandpuppy.module.pay.kakao.dto.KakaoPayInfoRequest;
import com.elice.kittyandpuppy.module.pay.kakao.service.KakaoPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class KakaoPayApiController {

    private final KakaoPayService kakaoPayService;
    private final TokenProvider tokenProvider;
    private final OrderService orderService;

    @PostMapping("/ready")
    public ResponseEntity<?> getRedirectUrl(@RequestBody KakaoPayInfoRequest payInfoDto) {
        try {
            Order order = orderService.findById(payInfoDto.getOrderId());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(kakaoPayService.getRedirectUrl(order, tokenProvider.getMemberId(payInfoDto.getToken())));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/success")
    public ResponseEntity<?> afterGetRedirectUrl(@RequestParam("memberId") Long memberId,
                                                 @RequestParam("orderId") Long orderId,
                                                 @RequestParam("pg_token") String pgToken) {
        try {
            KakaoPayApproveResponse kakaoApprove = kakaoPayService.getApprove(memberId, orderId, pgToken);

            return ResponseEntity.ok().body(kakaoApprove);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 결제 진행 중 취소
     */
    @GetMapping("/cancel")
    public ResponseEntity<?> cancel() {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body("결제를 취소했습니다.");
    }

    /**
     * 결제 실패
     */
    @GetMapping("/fail")
    public ResponseEntity<?> fail() {

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body("결제가 실패하였습니다.");

    }

}