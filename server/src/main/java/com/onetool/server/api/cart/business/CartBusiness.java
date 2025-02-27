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
import com.onetool.server.global.exception.base.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.onetool.server.global.exception.codes.ErrorCode.NO_BLUEPRINT_FOUND;

@Business
@RequiredArgsConstructor
public class CartBusiness {

    private final CartService cartService;
    private final MemberService memberService;
    private final BlueprintService blueprintService;

    @Transactional
    public void addBlueprintToCart(Long userId, Long blueprintId) {
        Member memberWithCart = memberService.findMemberWithCart(userId);
        Blueprint blueprint = blueprintService.findBlueprintById(blueprintId);
        Cart cart = memberWithCart.getCart();
        cartService.validateBlueprintAlreadyInCart(cart, blueprint);
        cartService.saveCart(cart, blueprint);
        cart.updateTotalPrice(cart);
    }

    @Transactional
    public Object getMyCart(Long userId) {
        Cart cart = cartService.findCartById(userId);
        Long totalPrice = cartService.findTotalPrice(cart.getId());
        List<CartBlueprint> cartBlueprintList = cartService.findCartBlueprint(cart.getId());

        if (cartBlueprintList == null) return "장바구니가 비었습니다.";
        return CartItemsResponse.cartItems(totalPrice, cartBlueprintList);
    }

    @Transactional
    public String removeBlueprintInCart(Long userId, Long blueprintId) {
        Member member = memberService.findMemberWithCart(userId);
        Cart cart = member.getCart();
        CartBlueprint cartBlueprint = findCartBlueprint(cart, blueprintId);
        cartService.deleteCartBlueprint(cartBlueprint);
        cart.updateTotalPrice(cart);

        return "삭제되었습니다";
    }

    private CartBlueprint findCartBlueprint(Cart cart, Long blueprintId) {
        return cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getBlueprint().getId().equals(blueprintId))
                .findFirst()
                .orElseThrow(() -> new BaseException(NO_BLUEPRINT_FOUND));
    }
}
