package com.elice.kittyandpuppy.module.pay.kakao.service;

import com.elice.kittyandpuppy.module.member.entity.Member;
import com.elice.kittyandpuppy.module.member.repository.MemberRepository;
import com.elice.kittyandpuppy.module.order.entity.Order;
import com.elice.kittyandpuppy.module.pay.kakao.dto.KakaoPayApproveResponse;
import com.elice.kittyandpuppy.module.pay.kakao.dto.KakaoPayReadyResponse;
import com.elice.kittyandpuppy.module.pay.kakao.dto.KakaoPayRequest;
import com.elice.kittyandpuppy.module.pay.kakao.util.KakaoPayRequestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class KakaoPayService {

    private final MemberRepository memberRepository;
    private final KakaoPayRequestUtil kakaoPayRequestUtil;

    @Value("${pay.secret-key}")
    private String secretKey;


    /**
     * 카오페이 결제를 시작하기 위해 상세 정보를 카카오페이 서버에 전달하고 결제 고유 번호(TID)를 받는 단계입니다.
     * 어드민 키를 헤더에 담아 파라미터 값들과 함께 POST로 요청합니다.
     * 테스트  가맹점 코드로 'TC0ONETIME'를 사용
     */
    @Transactional
    public KakaoPayReadyResponse getRedirectUrl(Order order, Long memberId) throws Exception {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new Exception("해당 유저가 존재하지 않습니다."));

        HttpHeaders headers = new HttpHeaders();

        /** 요청 헤더 */
        String auth = "SECRET_KEY " + secretKey;
        headers.set("Content-type", "application/json");
        headers.set("Authorization", auth);

        /** 요청 Body */
        KakaoPayRequest payRequest = kakaoPayRequestUtil.getReadyRequest(memberId, order);

        /** Header와 Body 합쳐서 RestTemplate로 보내기 위한 밑작업 */
        HttpEntity<String> urlRequest = new HttpEntity<>(payRequest.getJsonObject().toString(), headers);

        /** RestTemplate로 Response 받아와서 DTO로 변환후 return */
        RestTemplate rt = new RestTemplate();
        KakaoPayReadyResponse payReadyResDto = rt.postForObject(payRequest.getUrl(), urlRequest, KakaoPayReadyResponse.class);
        member.updateTid(payReadyResDto.getTid());

        return payReadyResDto;
    }

    @Transactional
    public KakaoPayApproveResponse getApprove(Long memberId, Long orderId, String pgToken) throws Exception {


        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new Exception("해당 유저가 존재하지 않습니다."));

        String tid = member.getTid();

        HttpHeaders headers = new HttpHeaders();
        String auth = "SECRET_KEY " + secretKey;

        /** 요청 헤더 */
        headers.set("Content-type", "application/json");
        headers.set("Authorization", auth);

        /** 요청 Body */
        KakaoPayRequest payRequest = kakaoPayRequestUtil.getApproveRequest(tid, memberId, orderId, pgToken);


        /** Header와 Body 합쳐서 RestTemplate로 보내기 위한 밑작업 */
        HttpEntity<String> requestEntity = new HttpEntity<>(payRequest.getJsonObject().toString(), headers);

        // 요청 보내기
        RestTemplate rt = new RestTemplate();
        KakaoPayApproveResponse payApproveResDto = rt.postForObject(payRequest.getUrl(), requestEntity, KakaoPayApproveResponse.class);


        return payApproveResDto;


    }
}
