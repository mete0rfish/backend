package com.onetool.server.api.order.service;

import com.onetool.server.api.order.dto.request.OrderRequest;
import com.onetool.server.api.order.dto.response.OrderResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"test"})
class OrderServiceImplTest {

    private final static String ADMIN_EMAIL = "admin@example.com";

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @Test
    @DisplayName("주문 생성")
    void createOrderTest() {
        OrderRequest orderRequest = new OrderRequest(
                new HashSet<>(List.of(1L, 2L, 3L))
        );
        orderServiceImpl.makeOrder(ADMIN_EMAIL, orderRequest);

        List<OrderResponse. MyPageOrderResponseDto> response
                = orderServiceImpl.getMyPageOrder(ADMIN_EMAIL);

        assertThat(response.size()).isEqualTo(1);
    }
}