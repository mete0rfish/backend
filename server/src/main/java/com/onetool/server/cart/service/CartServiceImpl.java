package com.onetool.server.cart.service;

import com.onetool.server.blueprint.Blueprint;
import com.onetool.server.blueprint.repository.BlueprintRepository;
import com.onetool.server.cart.Cart;
import com.onetool.server.cart.CartBlueprint;
import com.onetool.server.cart.repository.CartBlueprintRepository;
import com.onetool.server.cart.repository.CartRepository;
import com.onetool.server.global.auth.MemberAuthContext;
import com.onetool.server.global.exception.BaseException;
import com.onetool.server.member.domain.Member;
import com.onetool.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.onetool.server.cart.dto.CartResponse.CartItems;
import static com.onetool.server.global.exception.codes.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final BlueprintRepository blueprintRepository;
    private final CartBlueprintRepository cartBlueprintRepository;

    public Object showCart(MemberAuthContext user){
        Member member = findMemberWithCart(user.getId());
        Cart cart = member.getCart();
        if(cart.isCartEmpty()) return "장바구니에 상품이 없습니다.";
        return CartItems.cartItems(cart.getTotalPrice(), cart.getCartItems());
    }

    public String addBlueprintToCart(MemberAuthContext user,
                                     Long blueprintId){
        Member member = findMemberWithCart(user.getId());
        Cart cart = member.getCart();
        Blueprint blueprint = getBlueprint(blueprintId);
        if(cartBlueprintRepository.existsByCartAndBlueprint(cart, blueprint)){
            throw new BaseException(ALREADY_EXIST_BLUEPRINT_IN_CART);
        }

        CartBlueprint newCartBlueprint = CartBlueprint.addBlueprintToCart(cart, blueprint);

        cartBlueprintRepository.save(newCartBlueprint);
        cart.getCartItems().add(newCartBlueprint);

        cart.updateTotalPrice(cart.getCartItems().stream()
                .mapToLong(item -> item.getBlueprint().getStandardPrice())
                .sum());

        cartRepository.save(cart);
        return "장바구니에 추가됐습니다.";
    }

    public String deleteBlueprintInCart(MemberAuthContext user,
                                      Long blueprintId){
        Member member = findMemberWithCart(user.getId());
        Cart cart = member.getCart();
        CartBlueprint cartBlueprint = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getBlueprint().getId().equals(blueprintId))
                .findFirst()
                .orElseThrow(() -> new BaseException(NO_BLUEPRINT_FOUND));

        cartBlueprintRepository.delete(cartBlueprint);
        cart.getCartItems().remove(cartBlueprint);

        cart.updateTotalPrice(cart.getCartItems().stream()
                .mapToLong(item -> item.getBlueprint().getStandardPrice())
                .sum());

        cartRepository.save(cart);
        return "삭제됐습니다.";
    }

    public Blueprint getBlueprint(Long blueprintId) {
        return blueprintRepository.findById(blueprintId).orElseThrow(() -> new BaseException(NO_BLUEPRINT_FOUND));
    }

    public Member findMemberWithCart(Long id) {
        return memberRepository
                .findByIdWithCart(id)
                .orElseThrow(() -> new BaseException(NON_EXIST_USER));
    }
}