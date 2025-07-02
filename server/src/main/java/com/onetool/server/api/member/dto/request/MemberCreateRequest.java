package com.onetool.server.api.member.dto.request;

import com.onetool.server.api.member.domain.DateTimeFormat;
import com.onetool.server.api.member.domain.Member;
import com.onetool.server.api.member.enums.UserRole;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MemberCreateRequest(
        String email,
        String password,
        String name,
        String birthDate,
        String development_field,
        String phoneNum,
        boolean isNative
) {

}