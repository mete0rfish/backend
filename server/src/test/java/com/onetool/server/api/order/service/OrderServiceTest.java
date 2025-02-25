package com.onetool.server.api.order.service;

import com.onetool.server.api.order.dto.response.OrderResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
//@ActiveProfiles({"test", "local"})
//class OrderServiceTest {
//
//    @Autowired
//    private OrderService orderService;
//
//    @Test
//    @DisplayName("주문 생성")
//    void createOrderTest() {
//        // given
//
//        // when
//        Long orderId = createOrder();
//        List<OrderResponse. MyPageOrderResponseDto> response
//                = orderService.getMyPageOrder(OrderFixture.ADMIN_EMAIL);
//
//        // then
//        assertThat(response.size()).isEqualTo(1);
//    }
//
//    @Test
//    @DisplayName("주문 삭제")
//    void deleteOrderTest() {
//        // given
//        Long orderId = createOrder();
//
//        // when
//        orderService.deleteOrder(orderId);
//
//        // then
//        List<OrderResponse. MyPageOrderResponseDto> response
//                = orderService.getMyPageOrder(OrderFixture.ADMIN_EMAIL);
//        assertThat(response.size()).isEqualTo(0);
//    }
//
//    private Long createOrder() {
//        return orderService.saveOrder(OrderFixture.ADMIN_EMAIL, OrderFixture.ORDER_REQUEST);
//    }
//}