package com.onetool.server.api.cart.dto.response;

import com.onetool.server.api.blueprint.dto.response.BlueprintResponse;
import com.onetool.server.api.cart.Cart;
import com.onetool.server.api.cart.CartBlueprint;
import com.onetool.server.api.cart.dto.CartResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record CartItemsResponse(
        Long totalPrice,
        List<CartItemResponse> blueprintsInCart
) {
    public static CartItemsResponse from(Cart cart) {
        return CartItemsResponse.builder()
                .totalPrice(cart.getTotalPrice())
                .blueprintsInCart(cart.getCartItems().stream()
                        .map(CartItemResponse::from)
                        .toList())
                .build();
    }
}