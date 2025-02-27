package com.onetool.server.api.payments.service;

import com.onetool.server.api.order.Orders;
import com.onetool.server.api.payments.domain.Payment;
import com.onetool.server.api.payments.repository.PaymentRepository;
import com.onetool.server.global.exception.OrdersNullPointException;
import com.onetool.server.global.exception.PaymentNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional(readOnly = true)
    public Payment findByOrders(Orders orders) {
        return paymentRepository.findByOrders(orders);
    }

    @Transactional(readOnly = true)
    public Payment findById(Long id) {
        return paymentRepository.findById(id).orElseThrow(() -> new PaymentNotFoundException(id));
    }

    @Transactional
    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Transactional
    public void deleteById(Payment payment) {
        validateOrdersIsNull(payment);
        paymentRepository.delete(payment);
    }

    private void validateOrdersIsNull(Payment payment) {
        if (payment == null) {
            throw new OrdersNullPointException("payment가 NULL입니다. 함수명 : validatePaymentIsNull");
        }
    }
}
