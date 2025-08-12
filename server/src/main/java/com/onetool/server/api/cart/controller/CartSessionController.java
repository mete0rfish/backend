package com.onetool.server.api.cart.controller;

import com.onetool.server.api.cart.dto.response.CartSessionResponse;
import com.onetool.server.api.cart.service.CartSessionService;
import com.onetool.server.global.exception.ApiResponse;
import com.onetool.server.global.exception.codes.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
public class CartSessionController {

    private final CartSessionService cartSessionService;

    @PostMapping("cart/session/add")
    public ApiResponse<?> addCartItemsFromSession(HttpServletRequest httpServletRequest, @RequestBody List<Long> cartItemIds) {
        HttpSession session = httpServletRequest.getSession(false);
        session.setAttribute("cartItemIds", cartItemIds);
        return ApiResponse.onSuccess("세션에 아이템(들)이 추가되었습니다.");
    }

    @PatchMapping("/cart/session/remove")
    public ApiResponse<?> removeCartItemsFromSession(HttpServletRequest httpServletRequest, @RequestBody Long cartItemId) {
        HttpSession session = httpServletRequest.getSession();
        List<Long> items = (List<Long>) session.getAttribute("cartItemIds");
        if (items != null) {
            items.remove(cartItemId);
            session.setAttribute("itemList", items);
            return ApiResponse.onSuccess("해당 아이템이 세션에서 제거되었습니다.");
        } else {
            return ApiResponse.onFailure(ErrorCode.BAD_REQUEST.name(), "세션에 존재하지 않는 아이템입니다.", null);
        }
    }

    @GetMapping("/cart/session/get")
    public ApiResponse<?> getCartItemsFromSession(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        List<Long> items = (List<Long>) session.getAttribute("cartItemIds");
        List<CartSessionResponse> responses = cartSessionService.getCartSessions(items);
        if (responses != null) {
            return ApiResponse.onSuccess(responses);
        } else {
            return ApiResponse.onFailure(ErrorCode.BAD_REQUEST.name(), "세션에 아이템이 존재하지 않습니다.", null);
        }
    }

    @DeleteMapping("/cart/session/drop")
    public ApiResponse<?> dropCartItemsFromSession(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        session.invalidate();
        return ApiResponse.onSuccess("세션 제거가 완료되었습니다.");
    }
}
