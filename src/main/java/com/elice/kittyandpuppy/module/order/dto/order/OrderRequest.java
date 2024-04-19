package com.elice.kittyandpuppy.module.order.dto.order;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderRequest {
    private String token;
    private Long deliveryId;
    private List<Long> orderItemIds;
    private String payment;
}