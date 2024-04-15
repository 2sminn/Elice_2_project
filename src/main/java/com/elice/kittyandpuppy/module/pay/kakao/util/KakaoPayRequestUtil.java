package com.elice.kittyandpuppy.module.pay.kakao.util;

import com.elice.kittyandpuppy.module.order.entity.Order;
import com.elice.kittyandpuppy.module.pay.kakao.dto.KakaoPayRequest;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoPayRequestUtil {
    public KakaoPayRequest getReadyRequest(Long memberId, Order order){

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("cid","TC0ONETIME");
        jsonObject.put("partner_order_id", order.getId());
        jsonObject.put("partner_user_id", memberId);

        if (order.getOrderItems().size() != 1) {
            int size = order.getOrderItems().size();
            String itemName = order.getOrderItems().get(0).getProduct().getName();

            jsonObject.put("item_name", String.format("%s 외 %d건", itemName, size - 1));
            jsonObject.put("quantity", size);
        } else {
            jsonObject.put("item_name", order.getOrderItems().get(0).getProduct().getName());
            jsonObject.put("quantity", 1);
        }

        jsonObject.put("total_amount", order.getTotalPrice());
        jsonObject.put("tax_free_amount", "0");
        jsonObject.put("approval_url", "http://localhost:8080/order/kakaopay"+"?memberId=" + memberId + "&orderId=" + order.getId()); // 성공 시 redirect url
        jsonObject.put("cancel_url", "http://localhost:8080/order/kakaopay/fail"); // 취소 시 redirect url
        jsonObject.put("fail_url", "http://localhost:8080/order/kakaopay/fail"); // 실패 시 redirect url

        return new KakaoPayRequest("https://open-api.kakaopay.com/online/v1/payment/ready", jsonObject);
    }

    public KakaoPayRequest getApproveRequest(String tid, Long memberId, Long orderId, String pgToken){

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("cid", "TC0ONETIME");
        jsonObject.put("tid", tid);
        jsonObject.put("partner_order_id", orderId); // Order_id
        jsonObject.put("partner_user_id", memberId); // Member_id

        jsonObject.put("pg_token", pgToken);

        return new KakaoPayRequest("https://open-api.kakaopay.com/online/v1/payment/approve", jsonObject);
    }
}
