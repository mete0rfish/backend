package com.onetool.server.cart.service;

import com.onetool.server.blueprint.Blueprint;
import com.onetool.server.global.auth.MemberAuthContext;
import com.onetool.server.member.Member;

import static com.onetool.server.cart.dto.CartResponse.*;

public interface CartService {
    CartItems showCart(MemberAuthContext user);

    void addBlueprintToCart(MemberAuthContext user, Long blueprintId);

    void deleteBlueprintInCart(MemberAuthContext user, Long blueprintId);

    Member findMemberWithCart(Long id);

    Blueprint getBlueprint(Long blueprintId);
}