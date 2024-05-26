package com.elice.kittyandpuppy.module.cart.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequest {
    private Long productId;
    private String token;
}
