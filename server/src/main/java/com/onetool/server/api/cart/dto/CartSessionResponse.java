package com.onetool.server.api.cart.dto;

import lombok.Builder;

@Builder
public record CartSessionResponse(
        Long blueprintId,
        String blueprintName,
        String extension,
        String author,
        Long price
) {

}
