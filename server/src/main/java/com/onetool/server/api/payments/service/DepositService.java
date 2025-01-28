package com.onetool.server.api.payments.service;

import com.onetool.server.api.order.Orders;
import com.onetool.server.api.order.repository.OrderRepository;
import com.onetool.server.api.payments.domain.Payment;
import com.onetool.server.api.payments.domain.PaymentBlueprint;
import com.onetool.server.api.payments.dto.DepositRequest;
import com.onetool.server.api.payments.dto.DepositResponse;
import com.onetool.server.api.payments.repository.DepositRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DepositService {

    private final DepositRepository depositRepository;
    private final OrderRepository orderRepository;

    public List<DepositResponse> getDeposits(Long userId) {
        List<Orders> ordersList = orderRepository.findByUserId(userId);
        List<Payment> paymentList = getPaymentsByOredes(ordersList);
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
                            .accountName(payment.getAccountName())
                            .accountNumber(payment.getAccountNumber())
                            .blueprintIds(getOrdersBlueprintIds(payment.getOrders()))
                            .bankName(payment.getBankName())
                            .totalPrice(payment.getTotalPrice())
                            .build());
        });
        return responses;
    }

    private Set<Long> getOrdersBlueprintIds(Orders orders) {
        Set<Long> blueprintIds = new HashSet<>();
        orders.getOrderItems().forEach((orderItem) -> {
            blueprintIds.add(orderItem.getBlueprint().getId());
        });
        return blueprintIds;
    }

    @Transactional
    public void createDeposit(DepositRequest request) {
        Payment payment = buildPayment(request);
        depositRepository.save(payment);
    }

    private Set<PaymentBlueprint> mapToPaymentBlueprints(Set<Long> blueprintIds) {
        return blueprintIds.stream()
                .map(blueprintId -> PaymentBlueprint.builder()
                        .blueprint_id(blueprintId)
                        .build())
                .collect(Collectors.toSet());
    }

    private Payment buildPayment(DepositRequest request) {
        return Payment.builder()
                .accountNumber(request.accountNumber())
                .accountName(request.accountName())
                .bankName(request.bankName())
                .totalPrice(request.totalPrice())
                .build();
    }
}
