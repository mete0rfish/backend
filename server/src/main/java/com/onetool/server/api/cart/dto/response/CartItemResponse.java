package com.onetool.server.api.cart.dto.response;

import com.onetool.server.api.blueprint.Blueprint;
import com.onetool.server.api.cart.CartBlueprint;
import lombok.Builder;

@Builder
public record CartItemResponse(
        String blueprintName,
        String extension,
        String author,
        Long standardPrice,
        Long salesPrice
) {
    public static CartItemResponse from(Blueprint blueprint) {
        return CartItemResponse.builder()
                .blueprintName(blueprint.getBlueprintName())
                .extension(blueprint.getExtension())
                .author(blueprint.getCreatorName())
                .standardPrice(blueprint.getStandardPrice())
                .salesPrice(blueprint.getSalePrice())
                .build();
    }
}
