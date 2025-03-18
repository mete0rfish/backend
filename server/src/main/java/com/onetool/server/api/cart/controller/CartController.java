package com.onetool.server.api.cart.controller;

import com.onetool.server.api.cart.business.CartBusiness;
import com.onetool.server.api.cart.service.CartService;
import com.onetool.server.global.auth.login.PrincipalDetails;
import com.onetool.server.global.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartBusiness cartBusiness;

    @PostMapping("/{blueprintId}")
    public ApiResponse<String> addBlueprintToCart(@AuthenticationPrincipal PrincipalDetails principal,
                                                  @PathVariable Long blueprintId) {
        cartBusiness.addBlueprintToCart(principal.getContext().getId(), blueprintId);
        return ApiResponse.onSuccess("장바구니에 상품이 등록 되었습니다.");
    }

    @GetMapping
    public ApiResponse<Object> showMyCart(@AuthenticationPrincipal PrincipalDetails principal) {
        return ApiResponse.onSuccess(cartBusiness.getMyCart(principal.getContext().getId()));
    }

    @DeleteMapping("/{blueprintId}")
    public ApiResponse<String> deleteBlueprintInCart(@AuthenticationPrincipal PrincipalDetails principal,
                                                     @PathVariable Long blueprintId) {
        return ApiResponse.onSuccess(cartBusiness.removeBlueprintInCart(principal.getContext().getId(), blueprintId));
    }
}