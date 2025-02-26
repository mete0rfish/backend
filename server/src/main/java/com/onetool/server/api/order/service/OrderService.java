package com.onetool.server.api.order.service;

import com.onetool.server.api.blueprint.Blueprint;
import com.onetool.server.api.member.service.MemberService;
import com.onetool.server.api.order.OrderBlueprint;
import com.onetool.server.api.order.Orders;
import com.onetool.server.api.order.repository.OrderRepository;
import com.onetool.server.global.exception.OrderNotFoundException;
import com.onetool.server.api.member.domain.Member;
import com.onetool.server.global.exception.OrdersNullPointException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public List<Orders> findAllOrdersByUserId(Long memberId) {
        return orderRepository.findByUserId(memberId);
    }

    @Transactional
    public Orders findOrdersById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    @Transactional
    public Long saveOrder(Orders orders, Member member, List<Blueprint> blueprintList) {

        validateOrdersIsNull(orders);

        assignAllConnectOrders(orders, member, blueprintList);
        orderRepository.save(orders);

        return orders.getId();
    }

    @Transactional
    public void deleteOrder(Orders orders) {
        validateOrdersIsNull(orders);

        orderRepository.delete(orders);
    }

    private void validateOrdersIsNull(Orders orders) {
        if (orders == null) {
            throw new OrdersNullPointException("Orders가 NULL입니다. 함수명 : validateOrdersIsNull");
        }
    }

    private void assignAllConnectOrders(Orders orders, Member member, List<Blueprint> blueprintList) {
        orders.assignMember(member);

        blueprintList.forEach(blueprint -> {
            OrderBlueprint orderBlueprint = new OrderBlueprint(blueprint.getDownloadLink());
            orderBlueprint.assignOrder(orders);
            orderBlueprint.assignBlueprint(blueprint);
        });
    }
}