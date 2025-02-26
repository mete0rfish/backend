package com.onetool.server.api.order.dto.response;

import com.onetool.server.api.blueprint.dto.BlueprintResponse;
import com.onetool.server.api.order.Orders;
import lombok.Builder;

import java.util.List;

@Builder
public record OrderCompleteResponse(
        Long totalPrice,
        List<BlueprintResponse> blueprints
){
    public static OrderCompleteResponse response(Orders orders){
        return OrderCompleteResponse.builder()
                .totalPrice(orders.getTotalPrice())
                .blueprints(orders.getOrderItems()
                        .stream()
                        .map(orderBlueprint ->
                                BlueprintResponse.items(orderBlueprint.getBlueprint()))
                        .toList())
                .build();
    }
}