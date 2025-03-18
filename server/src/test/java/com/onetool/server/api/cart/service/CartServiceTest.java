package com.onetool.server.api.cart.service;


import com.onetool.server.api.blueprint.Blueprint;
import com.onetool.server.api.cart.Cart;
import com.onetool.server.api.cart.CartBlueprint;
import com.onetool.server.api.cart.fixture.CartFixture;
import com.onetool.server.api.cart.repository.CartBlueprintRepository;
import com.onetool.server.api.cart.repository.CartRepository;
import com.onetool.server.api.member.domain.Member;
import com.onetool.server.api.member.fixture.MemberFixture;
import com.onetool.server.global.new_exception.exception.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@SuppressWarnings("NonAsciiCharacters")
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartBlueprintRepository cartBlueprintRepository;

    @InjectMocks
    private CartService cartService;

    private Cart cart;

    @BeforeEach
    public void setUp() {
        cart = CartFixture.createCartWithNoMember();
        List<Blueprint> blueprints = CartFixture.createBlueprints();
        CartFixture.createCartBlueprints(cart, blueprints);
    }

    @Test
    void 장바구니_id_조회_성공(){
        //given
        given(cartRepository.findCartWithMemberByMemberId(anyLong()))
                .willReturn(Optional.of(cart));

        //when
        Cart result = cartService.findCartById(anyLong());

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(cart);
        verify(cartRepository, times(1)).findCartWithMemberByMemberId(anyLong());
    }

    @Test
    void 장바구니_id_조회_실패(){
        ///given
        given(cartRepository.findCartWithMemberByMemberId(anyLong()))
                .willReturn(Optional.empty());

        //when & then
        assertThatThrownBy(()-> cartService.findCartById(anyLong()))
                .isInstanceOf(ApiException.class);
    }

    @Test
    void 장바구니_도면_추가_성공() {
        //given
        Blueprint newBlueprintToAddInCart = CartFixture.createBlueprint();
        int beforeSize = cart.getCartItems().size();

        //when
        cartService.saveCart(cart, newBlueprintToAddInCart);

        //then
        verify(cartBlueprintRepository, times(1)).save(any(CartBlueprint.class));
        assertThat(cart.getCartItems())
                .hasSize(beforeSize + 1);
        assertThat(cart.getCartItems())
                .extracting("blueprint")
                .contains(newBlueprintToAddInCart);
        assertThat(cart.getTotalPrice())
                .isEqualTo(100000L);
    }

    @Test
    void 장바구니_도면_추가_실패() {
        //given
        Blueprint newBlueprintToAddInCart = CartFixture.createBlueprint();
        cart.getCartItems().add(new CartBlueprint(cart, newBlueprintToAddInCart));

        // when & then
        assertThatThrownBy(() -> cartService.saveCart(cart, newBlueprintToAddInCart))
                .isInstanceOf(ApiException.class);
    }

    @Test
    void 장바구니_도면_삭제_성공(){
        //given
        CartBlueprint cartBlueprint = CartBlueprint.create(cart, CartFixture.createBlueprint());

        //when
        cartService.deleteCartBlueprint(cart, cartBlueprint);

        // then
        verify(cartBlueprintRepository, times(1)).deleteById(cartBlueprint.getId());
        assertThat(cart.getCartItems())
                .doesNotContain(cartBlueprint);
        assertThat(cart.getTotalPrice())
                .isEqualTo(60000L);
    }

    @Test
    void 장바구니_도면_삭제_실패(){
        // Given

        // When & Then
        // 현재 ApiException 인스턴스를 만들 떄 커스텀 에러 메세지를 에러 메세지로 초기화하지 않아 예외 발생 여부만 확인
        // TODO : 커스텀 메세지까지 확인
        assertThatThrownBy(() -> cartService.deleteCartBlueprint(cart, null))
                .isInstanceOf(ApiException.class);
    }
}
