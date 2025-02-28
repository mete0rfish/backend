package com.onetool.server.api.cart.dto.request;

import jakarta.validation.constraints.NotNull;

public record AddBlueprintToCartRequest(
        @NotNull Long blueprintId
) {
}