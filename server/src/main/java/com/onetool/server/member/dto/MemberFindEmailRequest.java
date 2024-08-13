package com.onetool.server.member.dto;

public record MemberFindEmailRequest(
        String name,
        String birth_date
) {
}
