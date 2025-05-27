package com.onetool.server.api.cart.business;

import com.onetool.server.api.blueprint.Blueprint;
import com.onetool.server.api.blueprint.service.BlueprintService;
import com.onetool.server.api.cart.Cart;
import com.onetool.server.api.cart.CartBlueprint;
import com.onetool.server.api.cart.dto.response.CartItemsResponse;
import com.onetool.server.api.cart.service.CartService;
import com.onetool.server.global.annotation.Business;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Business
@RequiredArgsConstructor
public class CartBusiness {

    private final CartService cartService;
    private final BlueprintService blueprintService;

    @Transactional
    public void addBlueprintToCart(Long userId, Long blueprintId) {
        Cart memberCart = cartService.findCartById(userId);
        Blueprint blueprint = blueprintService.findBlueprintById(blueprintId);
        cartService.saveCart(memberCart, blueprint);
    }

    @Transactional(readOnly = true)
    public Object getMyCart(Long userId) {
        Cart cart = cartService.findCartById(userId);
        if (cart.getCartItems().isEmpty()){
            return "장바구니가 비었습니다.";
        }
        return CartItemsResponse.from(cart);
    }

    @Transactional
    public String removeBlueprintInCart(Long userId, Long blueprintId) {
        Cart memberCart = cartService.findCartById(userId);
        CartBlueprint cartBlueprint = cartService.findCartBlueprint(memberCart, blueprintId);
        cartService.deleteCartBlueprint(memberCart, cartBlueprint);
        return "삭제되었습니다";
    }
}
