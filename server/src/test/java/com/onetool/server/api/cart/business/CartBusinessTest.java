package com.onetool.server.api.cart.business;

import com.onetool.server.api.blueprint.Blueprint;
import com.onetool.server.api.blueprint.service.BlueprintService;
import com.onetool.server.api.cart.Cart;
import com.onetool.server.api.cart.CartBlueprint;
import com.onetool.server.api.cart.dto.response.CartItemResponse;
import com.onetool.server.api.cart.dto.response.CartItemsResponse;
import com.onetool.server.api.cart.fixture.CartFixture;
import com.onetool.server.api.cart.service.CartService;
import com.onetool.server.api.member.domain.Member;
import com.onetool.server.api.member.service.MemberService;
import com.onetool.server.global.new_exception.exception.ApiException;
import com.onetool.server.global.new_exception.exception.error.CartErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@SuppressWarnings("NonAsciiCharacters")
public class CartBusinessTest {
    @Mock
    private CartService cartService;

    @Mock
    private MemberService memberService;

    @Mock
    private BlueprintService blueprintService;

    @InjectMocks
    private CartBusiness cartBusiness;

    private Member member;
    private Blueprint blueprint;
    private Cart cart;

    @BeforeEach
    public void setUp() {
        member = CartFixture.createMemberWithCart();
        blueprint = CartFixture.createBlueprintToAddInCart();
        cart = member.getCart();
    }

    @Test
    void 장바구니에_도면_추가_성공(){
        //given
        given(memberService.findOneWithCart(anyLong())).willReturn(member);
        given(blueprintService.findBlueprintById(anyLong())).willReturn(blueprint);
        doAnswer(invocation -> {
            Cart cartArg = invocation.getArgument(0);
            Blueprint blueprintArg = invocation.getArgument(1);
            cartArg.getCartItems().add(new CartBlueprint(cartArg, blueprintArg));
            return null;
        }).when(cartService).saveCart(any(Cart.class), any(Blueprint.class));
        int beforeSize = cart.getCartItems().size();

        //when
        cartBusiness.addBlueprintToCart(member.getId(), blueprint.getId());

        //then
        verify(cartService).saveCart(any(Cart.class), any(Blueprint.class));
        assertThat(cart.getCartItems())
                .hasSize(beforeSize + 1);
        assertThat(cart.getCartItems())
                .extracting("blueprint")
                .contains(blueprint);
        assertThat(cart.getTotalPrice())
                .isEqualTo(40000L);
    }

    @Test
    void 장바구니_조회_성공(){
        //given
        given(cartService.findCartById(anyLong())).willReturn(cart);

        //when
        CartBlueprint.create(cart, blueprint);
        CartItemsResponse result = (CartItemsResponse) cartBusiness.getMyCart(member.getId());

        //then
        assertThat(result.totalPrice())
                .isEqualTo(cart.getTotalPrice());

        assertThat(result.blueprintsInCart())
                .usingRecursiveComparison()
                .isEqualTo(List.of(CartItemResponse.from(blueprint)));
    }

    @Test
    void 장바구니_비어있음_성공(){
        //given
        given(cartService.findCartById(anyLong())).willReturn(cart);

        //when & then
        assertThat((String) cartBusiness.getMyCart(member.getId()))
                .isEqualTo("장바구니가 비었습니다.");
    }

    @Test
    void 장바구니_도면_삭제_성공(){
        //given
        CartBlueprint cartBlueprint = CartBlueprint.create(cart, blueprint);
        given(memberService.findOneWithCart(anyLong())).willReturn(member);
        given(cartService.findCartBlueprint(any(Cart.class), anyLong()))
                .willReturn(cartBlueprint);
        doAnswer(invocation -> {
            CartBlueprint cbArgs = invocation.getArgument(0);
            cart.getCartItems().remove(cbArgs);
            return null;
        }).when(cartService).deleteCartBlueprint(any(CartBlueprint.class));

        // when
        String result = cartBusiness.removeBlueprintInCart(member.getId(), blueprint.getId());

        // then
        assertThat(result).isEqualTo("삭제되었습니다");
        assertThat(member.getCart().getCartItems())
                .doesNotContain(cartBlueprint);
    }
}
