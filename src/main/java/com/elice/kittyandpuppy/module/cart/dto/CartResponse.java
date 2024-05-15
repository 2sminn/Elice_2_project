package com.elice.kittyandpuppy.module.cart.dto;


import com.elice.kittyandpuppy.module.product.entity.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartResponse {
    private List<Product> products;
}
