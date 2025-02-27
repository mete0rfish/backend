package com.onetool.server.api.cart.dto.response;

import com.onetool.server.api.blueprint.dto.response.BlueprintResponse;
import com.onetool.server.api.cart.CartBlueprint;
import com.onetool.server.api.cart.dto.CartResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record CartItemsResponse(
        Long totalPrice,
        List<BlueprintResponse> blueprintsInCart
) {
    public static CartItemsResponse cartItems(Long totalPrice, List<CartBlueprint> cartBlueprints) {
        return CartItemsResponse.builder()
                .totalPrice(totalPrice)
                .blueprintsInCart(cartBlueprints.stream()
                        .map(cartItem -> BlueprintResponse.items(cartItem.getBlueprint()))
                        .toList())
                .build();
    }
}