package com.onetool.server.api.blueprint.dto.success;

import lombok.Builder;

@Builder
public record BlueprintUpdateSuccess(
        boolean isSuccess,
        Long blueprintId
) {
}
