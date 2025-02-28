package com.onetool.server.api.member.dto;

import com.onetool.server.api.blueprint.Blueprint;
import com.onetool.server.api.order.OrderBlueprint;

public record BlueprintDownloadResponse(
        Long blueprintId,
        String blueprintImage,
        String blueprintDownloadLink,
        String blueprintName,
        String blueprintCreatorName
) {

    public static BlueprintDownloadResponse from(final OrderBlueprint orderItem) {
        final Blueprint blueprint = orderItem.getBlueprint();
        return new BlueprintDownloadResponse(
                blueprint.getId(),
                blueprint.getBlueprintImg(),
                blueprint.getDownloadLink(),
                blueprint.getBlueprintName(),
                blueprint.getCreatorName()
        );
    }
}