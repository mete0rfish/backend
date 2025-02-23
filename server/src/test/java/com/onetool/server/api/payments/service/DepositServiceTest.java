package com.onetool.server.api.payments.service;

import com.onetool.server.api.order.service.OrderFixture;
import com.onetool.server.api.order.service.OrderServiceImpl;
import com.onetool.server.api.payments.dto.DepositRequest;
import com.onetool.server.api.payments.repository.DepositRepository;
import com.onetool.server.global.exception.PaymentNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"test", "local"})
class DepositServiceTest {

    private final DepositRequest depositRequest = DepositRequest.builder()
            .orderId(1L)
            .accountName("홍길동")
            .accountNumber("32144-4141323")
            .bankName("국민")
            .totalPrice(10000L)
            .build();

    @Autowired
    private DepositService depositService;

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @Test
    @DisplayName("결제 정보 삭제")
    void deletePaymentTest() {
        // given
        Long orderId = createOrder();
        Long paymentId = depositService.createDeposit(depositRequest);

        // when
        depositService.deleteDeposit(paymentId);

        // then
        assertThrows(PaymentNotFoundException.class, () -> {
            depositService.findById(paymentId);
        });
    }

    private Long createOrder() {
        return orderServiceImpl.makeOrder(OrderFixture.ADMIN_EMAIL, OrderFixture.ORDER_REQUEST);
    }
}