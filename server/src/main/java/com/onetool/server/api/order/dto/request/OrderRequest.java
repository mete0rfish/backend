package com.onetool.server.api.order.dto.request;

import lombok.Builder;

import java.util.List;
import java.util.Set;

@Builder
public record OrderRequest(
        Set<Long> blueprintIds
) {}