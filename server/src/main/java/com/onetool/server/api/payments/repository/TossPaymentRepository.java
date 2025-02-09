package com.onetool.server.api.payments.repository;

import com.onetool.server.api.payments.domain.TossPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TossPaymentRepository extends JpaRepository<TossPayment, Long> {
}
