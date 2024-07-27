package com.onetool.server.cart.service;

import com.onetool.server.blueprint.Blueprint;
import com.onetool.server.blueprint.BlueprintRepository;
import com.onetool.server.blueprint.BlueprintResponse;
import com.onetool.server.cart.Cart;
import com.onetool.server.cart.CartBlueprint;
import com.onetool.server.cart.repository.CartBlueprintRepository;
import com.onetool.server.cart.repository.CartRepository;
import com.onetool.server.global.auth.MemberAuthContext;
import com.onetool.server.global.exception.BaseException;
import com.onetool.server.global.handler.MyExceptionHandler;
import com.onetool.server.member.Member;
import com.onetool.server.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.onetool.server.global.exception.codes.ErrorCode.*;
import static com.onetool.server.blueprint.BlueprintResponse.*;
import static com.onetool.server.cart.dto.CartResponse.*;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final BlueprintRepository blueprintRepository;
    private final CartBlueprintRepository cartBlueprintRepository;

    public CartItems showCart(MemberAuthContext user){
        Member member = findMemberWithCart(user.getId());
        Cart cart = member.getCart();
        if(cart == null) {
            throw new BaseException(NO_ITEM_IN_CART);
        }
        return CartItems.cartItems(cart.getTotalPrice(), cart.getCartItems());
    }


    public void addBlueprintToCart(MemberAuthContext user,
                                   Long blueprintId){
        Member member = findMemberWithCart(user.getId());
        Cart cart = member.getCart();
        if (cart == null) {
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        Blueprint blueprint = getBlueprint(blueprintId);
        CartBlueprint existingCartBlueprint = cartBlueprintRepository.findByCartAndBlueprint(cart, blueprint);

        // 새로운 상품을 추가합니다.
        CartBlueprint newCartBlueprint = CartBlueprint.newCartBlueprint(cart, blueprint);

        cartBlueprintRepository.save(newCartBlueprint);
        cart.getCartItems().add(newCartBlueprint);

        // 총 가격을 업데이트합니다. 일단 정가를 더했음
        cart.updateTotalPrice(cart.getCartItems().stream()
                .mapToLong(item -> item.getBlueprint().getStandardPrice())
                .sum());

        cartRepository.save(cart);

    }

    public void deleteBlueprintInCart(MemberAuthContext user,
                                      Long blueprintId){
        Member member = findMemberWithCart(user.getId());
        Cart cart = member.getCart();
        if(cart == null){
            throw new BaseException(NO_ITEM_IN_CART);
        }
        CartBlueprint cartBlueprint = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getBlueprint().getId().equals(blueprintId))
                .findFirst()
                .orElseThrow(() -> new BaseException(NO_BLUEPRINT_FOUND));
        cart.getCartItems().remove(cartBlueprint);
        cartBlueprintRepository.delete(cartBlueprint);

        cart.updateTotalPrice(cart.getCartItems().stream()
                .mapToLong(item -> item.getBlueprint().getStandardPrice())
                .sum());

        cartRepository.save(cart);
    }

    public Blueprint getBlueprint(Long blueprintId) {
        return blueprintRepository.findById(blueprintId).orElseThrow(() -> new BaseException(NO_BLUEPRINT_FOUND));
    }

    public Member findMemberWithCart(Long id) {
        return memberRepository
                .findByIdWithCart(id)
                .orElseThrow(() -> new MyExceptionHandler(NON_EXIST_USER));
    }
}