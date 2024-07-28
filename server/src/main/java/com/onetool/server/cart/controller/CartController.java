package com.onetool.server.cart.controller;

import com.onetool.server.cart.service.CartServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartServiceImpl cartService;


    /**
     * 도면 상세페이지에서 장바구니에 추가
     * @param user
     * @param blueprintId
     * @return
     */
//    @PostMapping("/api/cart/add/{blueprintId}")
//    public ApiResponse<?> addBlueprintToCart(@AuthUser CustomUserDetails user,
//                                              @PathVariable Long blueprintId){
//
//    }

}
