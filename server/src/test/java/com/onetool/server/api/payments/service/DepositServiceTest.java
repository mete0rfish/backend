package com.onetool.server.api.payments.service;

import com.onetool.server.api.order.service.OrderFixture;
import com.onetool.server.api.order.service.OrderService;
import com.onetool.server.global.exception.PaymentNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


//@SpringBootTest
//@ActiveProfiles({"test", "local"})
//class DepositServiceTest {
//
//    private final DepositRequest depositRequest = DepositRequest.builder()
//            .orderId(1L)
//            .accountName("홍길동")
//            .accountNumber("32144-4141323")
//            .bankName("국민")
//            .totalPrice(10000L)
//            .build();
//
//    @Autowired
//    private DepositService depositService;
//
//    @Autowired
//    private OrderService orderService;
//
//    @Test
//    @DisplayName("결제 정보 삭제")
//    void deletePaymentTest() {
//        // given
//        Long orderId = createOrder();
//        Long paymentId = depositService.createDeposit(depositRequest);
//
//        // when
//        depositService.deleteDeposit(paymentId);
//
//        // then
//        assertThrows(PaymentNotFoundException.class, () -> {
//            depositService.findById(paymentId);
//        });
//    }
//
//    private Long createOrder() {
//        return orderService.saveOrder(OrderFixture.ADMIN_EMAIL, OrderFixture.ORDER_REQUEST);
//    }
//}