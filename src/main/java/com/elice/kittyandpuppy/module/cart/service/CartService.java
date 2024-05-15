package com.elice.kittyandpuppy.module.cart.service;

import com.elice.kittyandpuppy.module.cart.dto.CartRequest;
import com.elice.kittyandpuppy.module.cart.entity.Cart;
import com.elice.kittyandpuppy.module.cart.entity.CartItem;
import com.elice.kittyandpuppy.module.cart.repository.CartItemRepository;
import com.elice.kittyandpuppy.module.cart.repository.CartRepository;
import com.elice.kittyandpuppy.module.member.repository.MemberRepository;
import com.elice.kittyandpuppy.module.product.entity.Product;
import com.elice.kittyandpuppy.module.product.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final MemberRepository memberRepository;
    private final ProductService productService;

    /**
     * memberId로 cart를 반환한다.
     * 만약 회원이 장바구니를 생성한 적이 없다면
     * 최초 1회만 cart를 생성하여 반환한다.
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

    /**
     * 장바구니에 상품을 추가한다.
     */
    public Cart addItemToCart(Long memberId, Long productId, int quantity) {
        Cart cart = getCartByMemberId(memberId);
        Product product = productService.getProductById(productId);

        //TODO 이미 추가한 상품일 때의 예외 처리는 나중에 할 예정
        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .product(product)
                .build();

        cart.getCartItems().add(cartItem);
        return cartRepository.save(cart);
    }

    /**
     * 장바구니 아이템을 삭제한다.
     */
    public Cart removeItem(Long memberId, Long productId) {
        Cart cart = getCartByMemberId(memberId);
//        Iterator<CartItem> iterator = cart.getCartItems().iterator();
//        while (iterator.hasNext()) {
//            CartItem cartItem = iterator.next();
//            if (cartItem.getProduct().getId().equals(productId)) {
//                iterator.remove();
//            }
//        }
        cart.getCartItems().removeIf(cartItem -> cartItem.getProduct().getId().equals(productId));
        return cartRepository.save(cart);
    }

//    /**
//     * 장바구니 아이템의 수량을 변경한다.
//     */
//
//    public Cart changeQuantity(Long memberId, int quantity) {
//        Cart cart = getCartByMemberId(memberId);
//    }

}
