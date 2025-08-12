package com.onetool.server.api.order.service;

import com.onetool.server.api.order.dto.request.OrderRequest;

import java.util.HashSet;
import java.util.List;

public class OrderFixture {

    public final static String ADMIN_EMAIL = "admin@example.com";
    public final static OrderRequest ORDER_REQUEST = new OrderRequest(
            new HashSet<>(List.of(1L, 2L, 3L))
    );
}
