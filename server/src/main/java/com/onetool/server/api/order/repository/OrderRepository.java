package com.onetool.server.api.order.repository;

import com.onetool.server.api.order.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    @Query("SELECT o FROM Orders o JOIN FETCH o.member m WHERE m.id = :userId")
    List<Orders> findByUserId(@Param("userId") Long userId);
}