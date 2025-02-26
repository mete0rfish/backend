package com.onetool.server.api.order.business;

import com.onetool.server.api.blueprint.Blueprint;
import com.onetool.server.api.blueprint.service.BlueprintService;
import com.onetool.server.api.member.domain.Member;
import com.onetool.server.api.member.service.MemberService;
import com.onetool.server.api.order.OrderBlueprint;
import com.onetool.server.api.order.Orders;
import com.onetool.server.api.order.dto.request.OrderRequest;
import com.onetool.server.api.order.service.OrderService;
import com.onetool.server.global.annotation.Business;
import com.onetool.server.global.auth.login.PrincipalDetails;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Business
@RequiredArgsConstructor
public class OrderBusiness {

    private final OrderService orderService;
    private final MemberService memberService;
    private final BlueprintService blueprintService;

    public Long createOrder(PrincipalDetails principalDetails, OrderRequest orderRequest) {
        Member member = memberService.findMember(principalDetails.getContext().getEmail());
        List<Blueprint> blueprintList = blueprintService.findAllBlueprintByIds(orderRequest.blueprintIds());
        Orders orders = new Orders(blueprintList);

        return orderService.saveOrder(orders, member, blueprintList);
    }
}
