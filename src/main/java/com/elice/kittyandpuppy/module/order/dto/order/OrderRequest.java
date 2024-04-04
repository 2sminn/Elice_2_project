package com.elice.kittyandpuppy.module.order.dto.order;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
// TODO: jwt 도입시, memberId를 받을 필요 없음
public class OrderRequest {
    private Long memberId;
    private Long deliveryId;
    private List<Long> orderItemIds;
}