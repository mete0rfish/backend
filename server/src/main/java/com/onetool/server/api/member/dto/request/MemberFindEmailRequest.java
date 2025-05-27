package com.onetool.server.api.member.dto.request;

public record MemberFindEmailRequest(
        String name,
        String phone_num
) {
}