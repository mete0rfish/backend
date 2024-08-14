package com.onetool.server.order.service;

import com.onetool.server.diabetes.Diabetes;
import com.onetool.server.diabetes.repository.DiabetesRepository;
import com.onetool.server.global.auth.MemberAuthContext;
import com.onetool.server.global.exception.BaseException;
import com.onetool.server.global.exception.codes.ErrorCode;
import com.onetool.server.member.domain.Member;
import com.onetool.server.member.repository.MemberRepository;
import com.onetool.server.order.OrderBlueprint;
import com.onetool.server.order.Orders;
import com.onetool.server.order.dto.request.OrderItem;
import com.onetool.server.order.dto.request.OrderRequest;
import com.onetool.server.order.repository.OrderDiabetesRepository;
import com.onetool.server.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.onetool.server.global.exception.codes.ErrorCode.*;
import static com.onetool.server.order.dto.response.OrderResponse.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final DiabetesRepository diabetesRepository;
    private final OrderDiabetesRepository orderDiabetesRepository;

    public OrderPageMemberResponseDto getMemberInfoForOrder(MemberAuthContext user, OrderRequest request){

        Member member = findMember(user.getEmail());
        List<Diabetes> diabetes = new ArrayList<>();
        for(OrderItem diabetesId : request.orderList()){
            diabetes.add(getDiabetes(diabetesId.foodId()));
        }
        return OrderPageMemberResponseDto
                .orderPage(request.totalPrice(), member, diabetes);
    }

    public OrderCompleteResponseDto createOrders(MemberAuthContext user,
                                                 OrderRequest request){
        Member member = findMember(user.getEmail());
        Orders orders = new Orders();
        orders.mappingToMember(member);

        for (OrderItem diabetesId : request.orderList()){
            OrderBlueprint purchasedDiabetes = orderDiabetesRepository
                    .save(OrderBlueprint.newOrderBluePrint(orders, getDiabetes(diabetesId.foodId())));
            orders.getOrderItems().add(purchasedDiabetes);
        }
        orders.setTotalPrice(request.totalPrice());
        orderRepository.save(orders);
        log.info("member order info : {}", member.getOrders().toString());
        return OrderCompleteResponseDto.response(orders);
    }

    private Member findMember(String email){
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(NON_EXIST_USER));
    }

    public Diabetes getDiabetes(Long foodId) {
        return diabetesRepository
                .findById(foodId)
                .orElseThrow(() -> new BaseException(NO_BLUEPRINT_FOUND));
    }
}
