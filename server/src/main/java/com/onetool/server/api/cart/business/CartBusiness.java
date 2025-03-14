package com.onetool.server.api.cart.business;

import com.onetool.server.api.blueprint.Blueprint;
import com.onetool.server.api.blueprint.service.BlueprintService;
import com.onetool.server.api.cart.Cart;
import com.onetool.server.api.cart.CartBlueprint;
import com.onetool.server.api.cart.dto.response.CartItemsResponse;
import com.onetool.server.api.cart.service.CartService;
import com.onetool.server.api.member.domain.Member;
import com.onetool.server.api.member.service.MemberService;
import com.onetool.server.global.annotation.Business;
import com.onetool.server.global.new_exception.exception.ApiException;
import com.onetool.server.global.new_exception.exception.error.CartErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Business
@RequiredArgsConstructor
public class CartBusiness {

    private final CartService cartService;
    private final MemberService memberService;
    private final BlueprintService blueprintService;

    @Transactional
    public void addBlueprintToCart(Long userId, Long blueprintId) {
        Member memberWithCart = memberService.findOneWithCart(userId);
        Blueprint blueprint = blueprintService.findBlueprintById(blueprintId);
        Cart cart = memberWithCart.getCart();
        cartService.validateBlueprintAlreadyInCart(cart, blueprint);
        cartService.saveCart(cart, blueprint);
        cart.updateTotalPrice();
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
        Member member = memberService.findOneWithCart(userId);
        Cart cart = member.getCart();
        CartBlueprint cartBlueprint = findCartBlueprint(cart, blueprintId);
        cartService.deleteCartBlueprint(cartBlueprint);
        cart.updateTotalPrice();
        return "삭제되었습니다";
    }

    private CartBlueprint findCartBlueprint(Cart cart, Long blueprintId) {
        return cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getBlueprint().getId().equals(blueprintId))
                .findFirst()
                .orElseThrow(() -> new ApiException(CartErrorCode.NOT_FOUND_ERROR, "해당하는 Cart에 bluePrintId와 일치하는 Cart가 존재하지 않습니다. cartId : " + cart.getId() + "blueprintId : " + blueprintId));
    }
}
