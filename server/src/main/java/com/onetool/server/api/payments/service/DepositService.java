package com.onetool.server.api.payments.service;

import com.onetool.server.api.order.Orders;
import com.onetool.server.api.order.repository.OrderRepository;
import com.onetool.server.api.payments.domain.Payment;
import com.onetool.server.api.payments.domain.PaymentBlueprint;
import com.onetool.server.api.payments.dto.DepositRequest;
import com.onetool.server.api.payments.dto.DepositResponse;
import com.onetool.server.api.payments.repository.DepositRepository;
import com.onetool.server.global.exception.BaseException;
import com.onetool.server.global.exception.codes.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DepositService {

    private final DepositRepository depositRepository;
    private final OrderRepository orderRepository;

    public List<DepositResponse> getDeposits(Long userId) {
        List<Orders> ordersList = orderRepository.findByUserId(userId);
        List<Payment> paymentList = getPaymentsByOredes(ordersList);
        log.info("paymentList size: {}", paymentList.size());
        return createDepositResponseWithPayment(paymentList);
    }

    private List<Payment> getPaymentsByOredes(List<Orders> ordersList) {
        List<Payment> payments = new ArrayList<>();
        ordersList.forEach(order -> {
            payments.add(
                    depositRepository.findPByOrders(order));
        });
        return payments;
    }

    private List<DepositResponse> createDepositResponseWithPayment(List<Payment> paymentList) {
        List<DepositResponse> responses = new ArrayList<>();
        paymentList.forEach(payment -> {
            responses.add(
                    DepositResponse.builder()
                            .id(payment.getId())
                            .accountName(payment.getAccountName())
                            .accountNumber(payment.getAccountNumber())
                            .bankName(payment.getBankName())
                            .totalPrice(payment.getTotalPrice())
                            .build());
        });
        return responses;
    }

    @Transactional
    public void createDeposit(DepositRequest request) {
        Payment payment = buildPayment(request);
        depositRepository.save(payment);
    }

    private Payment buildPayment(DepositRequest request) {
        return Payment.builder()
                .accountNumber(request.accountNumber())
                .accountName(request.accountName())
                .bankName(request.bankName())
                .totalPrice(request.totalPrice())
                .orders(findOrders(request.orderId()))
                .build();
    }

    private Orders findOrders(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new BaseException(ErrorCode.ORDER_NOT_FOUND));
    }
}
