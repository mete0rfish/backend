package com.onetool.server.api.cart.dto.response;

import com.onetool.server.api.cart.CartBlueprint;
import lombok.Builder;

@Builder
public record CartItemResponse(
        Long blueprintId,
        String blueprintName,
        String extension,
        String author,
        Long standardPrice,
        Long salesPrice
) {
    public static CartItemResponse from(CartBlueprint cartBlueprint) {
        return CartItemResponse.builder()
                .blueprintId(cartBlueprint.getId())
                .blueprintName(cartBlueprint.getBlueprint().getBlueprintName())
                .extension(cartBlueprint.getBlueprint().getExtension())
                .author(cartBlueprint.getBlueprint().getCreatorName())
                .standardPrice(cartBlueprint.getBlueprint().getStandardPrice())
                .salesPrice(cartBlueprint.getBlueprint().getSalePrice())
                .build();
    }
}
