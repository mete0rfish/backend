package com.onetool.server.api.member.dto.response;

import com.onetool.server.api.member.domain.Member;
import com.onetool.server.api.order.Orders;
import com.onetool.server.api.order.dto.response.OrderResponse;
import jakarta.validation.constraints.Past;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record MemberInfoResponse(
        Long id,
        String email,
        String password,
        String name,
        @Past LocalDate birthDate,
        String development_field,
        String phoneNum,
        LocalDate createdAt,
        List<OrderResponse.OrderCompleteResponseDto> orderCompleteResponseDtos
) {

    public static MemberInfoResponse from(Member member) {
        return MemberInfoResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .name(member.getName())
                .birthDate(member.getBirthDate())
                .development_field(member.getField())
                .phoneNum(member.getPhoneNum())
                .createdAt(member.getCreatedAt().toLocalDate())
                .orderCompleteResponseDtos(convertOrdersToResponse(member.getOrders()))
                .build();
    }

    private static List<OrderResponse.OrderCompleteResponseDto> convertOrdersToResponse(List<Orders> orders) {
        return orders.stream().map(OrderResponse.OrderCompleteResponseDto::response).toList();
    }
}