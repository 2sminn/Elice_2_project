package com.elice.kittyandpuppy.module.cart.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "장바구니 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartApiController {

}