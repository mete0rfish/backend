package com.onetool.server.api.order.controller;

import com.onetool.server.api.order.business.OrderBusiness;
import com.onetool.server.api.order.dto.request.OrderRequest;
import com.onetool.server.global.auth.login.PrincipalDetails;
import com.onetool.server.global.exception.ApiResponse;
import com.onetool.server.api.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.onetool.server.api.order.dto.response.OrderResponse.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderBusiness orderBusiness;
    private final OrderService orderService;

    @PostMapping
    public ApiResponse<Long> ordersPost(
            @AuthenticationPrincipal PrincipalDetails principal,
            @Valid @RequestBody OrderRequest request
    ) {
        Long orderId = orderBusiness.createOrder(principal, request);
        return ApiResponse.onSuccess(orderId);
    }

    @GetMapping
    public ApiResponse<List<MyPageOrderResponseDto>> ordersGet(@AuthenticationPrincipal PrincipalDetails principal) {
        return ApiResponse.onSuccess(orderService.getMyPageOrder(principal.getContext().getEmail()));
    }

    @DeleteMapping
    public ApiResponse<Long> ordersDelete(
            @RequestBody Long orderId
    ) {
        orderService.deleteOrder(orderId);
        return ApiResponse.onSuccess(orderId);
    }
}