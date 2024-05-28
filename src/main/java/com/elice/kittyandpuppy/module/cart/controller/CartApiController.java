package com.elice.kittyandpuppy.module.cart.controller;


import com.elice.kittyandpuppy.global.jwt.TokenProvider;
import com.elice.kittyandpuppy.module.cart.dto.CartRequest;
import com.elice.kittyandpuppy.module.cart.dto.CartResponse;
import com.elice.kittyandpuppy.module.cart.entity.Cart;
import com.elice.kittyandpuppy.module.cart.service.CartService;
import com.elice.kittyandpuppy.module.product.dto.ResponseProductDto;
import com.elice.kittyandpuppy.module.product.entity.Product;
import com.elice.kittyandpuppy.module.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "장바구니 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartApiController {
    private final ProductService productService;
    private final CartService cartService;
    private final TokenProvider tokenProvider;


    //TODO 회원인 경우만 구현 완료. 비회원인 경우엔 아직 몰?루
    @Operation(summary = "장바구니에 상품 추가", description = "로그인 한 회원이 장바구니 담기 버튼 클릭시 상품이 추가된다.")
    @PostMapping("/add")
    /**
     * 회원일 경우 장바구니 추가
     * 요청 값으로 Token, productId 필요
     */
    public String addToCart(@RequestBody CartRequest cartRequest) {
        Long memberId = tokenProvider.getMemberId(cartRequest.getToken());
        cartService.addItemToCart(memberId, cartRequest.getProductId(), 1);
        return "장바구니 추가가 성공적으로 완료되었습니다.";
    }
    @Operation(summary = "장바구니 주문", description = "로그인 한 회원이 장바구니에 담긴 상품들을 주문한다.")
    @GetMapping("/order")
    public ResponseEntity<CartResponse> orderCart(@RequestBody Map<String, String> tokenMap) {
        String token = tokenMap.get("token");
        //TODO 토큰 유효성 검사 로직 추가
        Long memberId = tokenProvider.getMemberId(token);
        Cart cart = cartService.getCartByMemberId(memberId);
        return new ResponseEntity<>(cart.toDTO(),HttpStatus.OK);
        
    }
    

    // 카트에 담긴 상품 전달
//    @GetMapping("/cart")
//    public ResponseEntity<List<ResponseProductDto>> getCartById(@RequestParam("cartList") int[] cartList) {
//        List<Product> products = productService.findByCart(cartList);
//        List<ResponseProductDto> productDtos = products.stream()
//                .map(Product::toResponseDto)
//                .collect(Collectors.toList());
//        return new ResponseEntity<>(productDtos, HttpStatus.OK);
//    }

}
