package com.elice.kittyandpuppy.module.cart.service;

import com.elice.kittyandpuppy.module.cart.dto.CartRequest;
import com.elice.kittyandpuppy.module.cart.entity.Cart;
import com.elice.kittyandpuppy.module.cart.repository.CartItemRepository;
import com.elice.kittyandpuppy.module.cart.repository.CartRepository;
import com.elice.kittyandpuppy.module.member.repository.MemberRepository;
import com.elice.kittyandpuppy.module.product.entity.Product;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final MemberRepository memberRepository;

    /**
     * memberId로 cart를 반환한다.
     * 만약 회원이 장바구니를 생성한 적이 없다면
     * 최초 1회 cart를 생성하여 반환한다.
     * @param memberId
     * @return
     */
    public Cart getCartByMemberId(Long memberId) {
        Cart cart = cartRepository.findByMemberId(memberId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setMember(memberRepository.findById(memberId).orElseThrow());
            return cartRepository.save(newCart);
        });
        return cart;
    }

    public Cart addItemToCart(Long memberId, Product product, int quantity) {
        Cart cart = getCartByMemberId(memberId);

    }

}
