package com.onetool.server.api.cart.business;

import com.onetool.server.api.blueprint.Blueprint;
import com.onetool.server.api.blueprint.service.BlueprintService;
import com.onetool.server.api.cart.Cart;
import com.onetool.server.api.cart.service.CartService;
import com.onetool.server.api.member.domain.Member;
import com.onetool.server.api.member.service.MemberService;
import com.onetool.server.global.annotation.Business;
import com.onetool.server.global.auth.MemberAuthContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Business
@RequiredArgsConstructor
public class CartBusiness {

    private final CartService cartService;
    private final MemberService memberService;
    private final BlueprintService blueprintService;

    @Transactional
    public void addBlueprintToCart(MemberAuthContext user, Long blueprintId) {
        Member memberWithCart = memberService.findMemberWithCart(user.getId());
        Blueprint blueprint = blueprintService.findBlueprintById(blueprintId);
        Cart cart = memberWithCart.getCart();
        cartService.isBlueprintAlreadyInCart(cart,blueprint);

        cartService.saveCart(cart,blueprint);
    }


}
