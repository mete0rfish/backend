package com.onetool.server.cart.controller;

import com.onetool.server.cart.service.CartServiceImpl;
import com.onetool.server.global.exception.BaseResponse;
import com.onetool.server.member.domain.CustomUserDetails;
import com.onetool.server.qna.security.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
//    public BaseResponse<?> addBlueprintToCart(@AuthUser CustomUserDetails user,
//                                              @PathVariable Long blueprintId){
//
//    }

}
