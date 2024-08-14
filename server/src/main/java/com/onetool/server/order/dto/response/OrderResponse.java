package com.onetool.server.order.dto.response;

import com.onetool.server.diabetes.Diabetes;
import com.onetool.server.diabetes.dto.DiabetesResponse;
import com.onetool.server.member.domain.Member;
import com.onetool.server.member.dto.MemberSimpleInfoDto;
import com.onetool.server.order.Orders;
import lombok.Builder;

import java.util.List;

public class OrderResponse {

    @Builder
    public record OrderPageMemberResponseDto(
            Long totalPrice,
            MemberSimpleInfoDto memberInfo,
            List<DiabetesResponse> items
    ){
        public static OrderPageMemberResponseDto orderPage(Long totalPrice, Member member, List<Diabetes> diabetes){
            return OrderPageMemberResponseDto.builder()
                    .totalPrice(totalPrice)
                    .memberInfo(MemberSimpleInfoDto.makeMemberSimpInfoDto(member))
                    .items(diabetes.stream().map(DiabetesResponse::items).toList())
                    .build();
        }
    }

    @Builder
    public record OrderCompleteResponseDto(
            String orderCode,
            Long totalPrice,
            List<DiabetesResponse> items
    ){
        public static OrderCompleteResponseDto response(Orders orders){
            return OrderCompleteResponseDto.builder()
                    .orderCode(orders.getOrderCode())
                    .totalPrice(orders.getTotalPrice())
                    .items(orders.getOrderItems()
                            .stream()
                            .map(orderBlueprint ->
                                    DiabetesResponse.items(orderBlueprint.getDiabetes()))
                            .toList())
                    .build();
        }
    }
}
