package com.onetool.server.api.cart.service;

import com.onetool.server.api.blueprint.Blueprint;
import com.onetool.server.api.blueprint.repository.BlueprintRepository;
import com.onetool.server.api.cart.dto.CartSessionResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class CartSessionService {

    private final BlueprintRepository blueprintRepository;

    @Transactional
    public List<CartSessionResponse> getCartSessions(List<Long> ids) {
        List<CartSessionResponse> sessions = new ArrayList<>();

        if(ids == null) {
            return null;
        }

        ids.forEach(id -> {
            blueprintRepository.findById(id).ifPresent(blueprint -> {
                sessions.add(blueprintToCartSessionResponse(blueprint));
            });
        });

        return sessions;
    }

    private CartSessionResponse blueprintToCartSessionResponse(Blueprint blueprint) {
        return CartSessionResponse.builder()
                .blueprintId(blueprint.getId())
                .blueprintName(blueprint.getBlueprintName())
                .extension(blueprint.getExtension())
                .author(blueprint.getCreatorName())
                .price(blueprint.getStandardPrice())
                .build();
    }

}
