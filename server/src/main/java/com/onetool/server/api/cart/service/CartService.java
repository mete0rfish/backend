package com.onetool.server.api.cart.service;

import com.onetool.server.api.blueprint.Blueprint;
import com.onetool.server.api.cart.Cart;
import com.onetool.server.api.cart.CartBlueprint;
import com.onetool.server.api.cart.repository.CartBlueprintRepository;
import com.onetool.server.api.cart.repository.CartRepository;
import com.onetool.server.global.new_exception.exception.ApiException;
import com.onetool.server.global.new_exception.exception.error.CartErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartBlueprintRepository cartBlueprintRepository;

    public Cart findCartById(Long userId) {
        return cartRepository.findCartWithMemberByMemberId(userId)
                .orElseThrow(() -> new ApiException(CartErrorCode.NOT_FOUND_ERROR,
                        "유저와 관련된 Cart가 없습니다. userId : "
                                + userId
                        )
                );
    }

    public void saveCart(Cart cart, Blueprint blueprint) {
        validateBlueprintAlreadyInCart(cart, blueprint.getId());
        CartBlueprint cartBlueprint = CartBlueprint.create(cart, blueprint);
        cartBlueprintRepository.save(cartBlueprint);
        cart.updateTotalPrice();
    }

    public void deleteCartBlueprint(Cart cart, CartBlueprint cartBlueprint) {
        if (cartBlueprint == null) {
            throw new ApiException(CartErrorCode.NULL_POINT_ERROR, "CartBlueprint가 NULL입니다");
        }
        cartBlueprintRepository.deleteById(cartBlueprint.getId());
        cartBlueprint.deleteCartBlueprint();
        cart.updateTotalPrice();
    }

    private void validateBlueprintAlreadyInCart(Cart cart, Long blueprintId) {
       if(cart.getCartItems()
               .stream()
               .anyMatch(cartBlueprint -> cartBlueprint.getBlueprint().getId().equals(blueprintId))) {
           throw new ApiException(CartErrorCode.ALREADY_EXIST_BLUEPRINT_IN_CART);
       }
    }

    public CartBlueprint findCartBlueprint(Cart cart, Long blueprintId) {
        return cart.getCartItems()
                .stream()
                .filter(cartItem -> cartItem.getBlueprint().getId().equals(blueprintId))
                .findFirst()
                .orElseThrow(() -> new ApiException(CartErrorCode.NOT_FOUND_ERROR,
                        "해당하는 Cart에 bluePrintId와 일치하는 Cart가 존재하지 않습니다. cartId : "
                                + cart.getId()
                                + "blueprintId : "
                                + blueprintId
                        )
                );
    }
}