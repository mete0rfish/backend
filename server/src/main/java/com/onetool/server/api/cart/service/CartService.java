package com.onetool.server.api.cart.service;

import com.onetool.server.api.blueprint.Blueprint;
import com.onetool.server.api.cart.Cart;
import com.onetool.server.api.cart.CartBlueprint;
import com.onetool.server.api.cart.repository.CartBlueprintRepository;
import com.onetool.server.api.cart.repository.CartRepository;
import com.onetool.server.global.exception.CartNotFoundException;
import com.onetool.server.global.exception.base.BaseException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.onetool.server.global.exception.codes.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    @PersistenceContext
    private final EntityManager entityManager;
    private final CartRepository cartRepository;
    private final CartBlueprintRepository cartBlueprintRepository;

    public Cart findCartById(Long userId) {
        return cartRepository.findCartWithMemberByMemberId(userId)
                .orElseThrow(() -> new CartNotFoundException(userId.toString()));
    }

    public Long findTotalPrice(Long cartId) {
        return cartRepository.findTotalPriceByCartId(cartId);
    }

    public List<CartBlueprint> findCartBlueprint(Long cartId) {
        return cartRepository.findCartBlueprintsByCartId(cartId);
    }

    public void saveCart(Cart cart, Blueprint blueprint) {
        CartBlueprint cartBlueprint = new CartBlueprint();
        cartBlueprint.assignBlueprint(blueprint);
        cartBlueprint.assignCart(cart);

        cartBlueprintRepository.save(cartBlueprint);
        entityManager.flush(); //todo test필요
    }

    public void deleteCartBlueprint(CartBlueprint cartBlueprint) {
        if (cartBlueprint == null) throw new NullPointerException("cartBlueprint는 NULL입니다.");

        cartBlueprintRepository.deleteById(cartBlueprint.getId());
        entityManager.flush(); //todo test필요
    }

    public void validateBlueprintAlreadyInCart(Cart cart, Blueprint blueprint) {
        if (cartBlueprintRepository.existsByCartAndBlueprint(cart, blueprint))
            throw new BaseException(ALREADY_EXIST_BLUEPRINT_IN_CART);
    }
}