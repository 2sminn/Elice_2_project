package com.elice.kittyandpuppy.module.cart.entity;


import com.elice.kittyandpuppy.global.BaseTimeEntity;
import com.elice.kittyandpuppy.module.cart.dto.CartResponse;
import com.elice.kittyandpuppy.module.member.entity.Member;
import com.elice.kittyandpuppy.module.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart")
public class Cart extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    
    //장바구니 상품
    @Getter
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();
    
    //회원 주문시 아이디
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @Setter
    private Member member;

    public CartResponse toDTO() {
        return new CartResponse(this.cartItems);
    }

}
