package com.onetool.server.api.blueprint.dto;

public record BlueprintSortRequest(
        Long categoryId,
        String sortBy,
        String sortOrder
) {}