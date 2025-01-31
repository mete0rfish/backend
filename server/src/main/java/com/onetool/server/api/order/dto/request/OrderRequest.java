package com.onetool.server.api.order.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;
import java.util.Set;

@Builder
public record OrderRequest(
        @NotEmpty
        Set<Long> blueprintIds
) {}