package com.onetool.server.api.order.service;

import com.onetool.server.api.blueprint.Blueprint;
import com.onetool.server.api.blueprint.repository.BlueprintRepository;
import com.onetool.server.api.member.service.MemberService;
import com.onetool.server.api.order.OrderBlueprint;
import com.onetool.server.api.order.Orders;
import com.onetool.server.api.order.dto.request.OrderRequest;
import com.onetool.server.api.order.dto.response.OrderResponse;
import com.onetool.server.api.order.repository.OrderBlueprintRepository;
import com.onetool.server.api.order.repository.OrderRepository;
import com.onetool.server.api.payments.service.DepositService;
import com.onetool.server.global.exception.BlueprintNotFoundException;
import com.onetool.server.global.exception.OrderNotFoundException;
import com.onetool.server.api.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final MemberService memberService;
    private final OrderRepository orderRepository;


    @Transactional
    public Long saveOrder(Orders orders, Member member, List<Blueprint> blueprintList) {

        if(orders==null){
            throw new OrderNotFoundException(123L);
        }

        assignAllConnectOrders(orders, member, blueprintList);
        orderRepository.save(orders);

        return orders.getId();
    }

    @Transactional
    public List<OrderResponse.MyPageOrderResponseDto> getMyPageOrder(String userEmail) {
        Member member = memberService.findMember(userEmail);
        List<Orders> ordersList = findOrdersByMemberId(member.getId());
        return OrderResponse.MyPageOrderResponseDto.from(ordersList);
    }

    private List<Orders> findOrdersByMemberId(Long memberId) {
        return new ArrayList<>(orderRepository.findByUserId(memberId));
    }

    public void deleteOrder(Long orderId) {
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
        orderRepository.delete(orders);
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