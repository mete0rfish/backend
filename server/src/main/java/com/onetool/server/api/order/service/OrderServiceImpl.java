package com.onetool.server.api.order.service;

import com.onetool.server.api.blueprint.Blueprint;
import com.onetool.server.api.blueprint.repository.BlueprintRepository;
import com.onetool.server.api.order.OrderBlueprint;
import com.onetool.server.api.order.Orders;
import com.onetool.server.api.order.dto.request.OrderRequest;
import com.onetool.server.api.order.dto.response.OrderResponse;
import com.onetool.server.api.order.repository.OrderRepository;
import com.onetool.server.api.payments.service.DepositService;
import com.onetool.server.global.exception.BlueprintNotFoundException;
import com.onetool.server.global.exception.base.BaseException;
import com.onetool.server.api.member.domain.Member;
import com.onetool.server.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.onetool.server.global.exception.codes.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final BlueprintRepository blueprintRepository;
    private final OrderRepository orderRepository;
    private final DepositService depositService;

    @Transactional
    public Long makeOrder(String userEmail, OrderRequest request) {
        Member member = findMember(userEmail);
        Orders orders = createOrders(request, member);
        return orders.getId();
    }

    private Orders createOrders(OrderRequest request, Member member) {
        List<Blueprint> blueprints = getBlueprintsByIds(request.blueprintIds());
        Orders orders = Orders.builder()
                .totalPrice(calcTotalPrice(blueprints))
                .member(member)
                .totalCount(request.blueprintIds().size())
                .build();
        orders.setOrderItems(createOrderBlueprints(blueprints, orders));
        return orderRepository.save(orders);
    }

    private List<Blueprint> getBlueprintsByIds(Set<Long> blueprintIds) {
        List<Blueprint> blueprints = new ArrayList<>();
        blueprintIds.forEach(blueprintId ->
                blueprints.add(
                        blueprintRepository.findById(blueprintId).orElseThrow(() -> new BlueprintNotFoundException(blueprintId.toString()))
        ));
        return blueprints;
    }

    private List<OrderBlueprint> createOrderBlueprints(List<Blueprint> blueprints, Orders orders) {
        List<OrderBlueprint> orderBlueprints = new ArrayList<>();
        blueprints.forEach(blueprint ->
            orderBlueprints.add(
                    OrderBlueprint.builder()
                            .blueprint(blueprint)
                            .order(orders)
                            .downloadUrl(blueprint.getDownloadLink())
                            .build()
            )
        );
        return orderBlueprints;
    }

    private Member findMember(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(NON_EXIST_USER));
    }

    private Long calcTotalPrice(List<Blueprint> blueprints) {
        return blueprints.stream().mapToLong(Blueprint::getStandardPrice).sum();
    }

    @Transactional
    public List<OrderResponse.MyPageOrderResponseDto> getMyPageOrder(String userEmail) {
        Member member = findMember(userEmail);
        List<Orders> ordersList = findOrdersByMemberId(member.getId());
        return OrderResponse.MyPageOrderResponseDto.from(ordersList);
    }

    private List<Orders> findOrdersByMemberId(Long memberId) {
        return new ArrayList<>(orderRepository.findByUserId(memberId));
    }
}