package com.onetool.server.api.payments.repository;

import com.onetool.server.api.order.Orders;
import com.onetool.server.api.payments.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepositRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT p FROM Payment p WHERE p.orders = :orders")
    Payment findPByOrders(@Param("orders") Orders orders);
}
