package com.onetool.server.api.member.dto.response;

import com.onetool.server.api.blueprint.Blueprint;
import com.onetool.server.api.order.OrderBlueprint;
import lombok.Builder;

import java.util.List;

@Builder
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

    public static List<BlueprintDownloadResponse> from(final List<OrderBlueprint> orderBlueprints) {
        return orderBlueprints.stream().map(BlueprintDownloadResponse::from).toList();
    }
}