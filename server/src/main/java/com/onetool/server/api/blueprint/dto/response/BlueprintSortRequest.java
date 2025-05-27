package com.onetool.server.api.blueprint.dto.response;

public record BlueprintSortRequest(
        String categoryName,
        String sortBy,
        String sortOrder
) {
}