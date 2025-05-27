package com.onetool.server.api.order.repository;

import com.onetool.server.api.order.OrderBlueprint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderBlueprintRepository extends JpaRepository<OrderBlueprint, Long> {
}