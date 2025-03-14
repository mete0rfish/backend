package com.onetool.server.api.cart.service;

import com.onetool.server.api.blueprint.Blueprint;
import com.onetool.server.api.cart.Cart;
import com.onetool.server.api.cart.CartBlueprint;
import com.onetool.server.api.cart.repository.CartBlueprintRepository;
import com.onetool.server.api.cart.repository.CartRepository;
import com.onetool.server.global.exception.CartNotFoundException;
import com.onetool.server.global.exception.base.BaseException;
import com.onetool.server.global.new_exception.exception.ApiException;
import com.onetool.server.global.new_exception.exception.error.CartErrorCode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.onetool.server.global.exception.codes.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartBlueprintRepository cartBlueprintRepository;

    public Cart findCartById(Long userId) {
        return cartRepository.findCartWithMemberByMemberId(userId)
                .orElseThrow(() -> new ApiException(CartErrorCode.NOT_FOUND_ERROR,"유저와 관련된 Cart가 없습니다. userId : "+ userId));
    }

    public void saveCart(Cart cart, Blueprint blueprint) {
        CartBlueprint cartBlueprint = CartBlueprint.create(cart, blueprint);
        cartBlueprintRepository.save(cartBlueprint);
    }

    public void deleteCartBlueprint(CartBlueprint cartBlueprint) {
        if (cartBlueprint == null) throw new ApiException(CartErrorCode.NULL_POINT_ERROR,"CartBlueprint가 NULL입니다");

        cartBlueprintRepository.deleteById(cartBlueprint.getId());
    }

    public void validateBlueprintAlreadyInCart(Cart cart, Blueprint blueprint) {
        if (cartBlueprintRepository.existsByCartAndBlueprint(cart, blueprint))
            throw new ApiException(CartErrorCode.ALREADY_EXIST_BLUEPRINT_IN_CART);
    }
}