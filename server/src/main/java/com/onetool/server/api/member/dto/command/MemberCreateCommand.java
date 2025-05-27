package com.onetool.server.api.member.dto.command;

import com.onetool.server.api.member.domain.DateTimeFormat;
import com.onetool.server.api.member.domain.Member;
import com.onetool.server.api.member.dto.request.MemberCreateRequest;
import com.onetool.server.api.member.enums.UserRole;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MemberCreateCommand(
        String email,
        String password,
        String name,
        String birthDate,
        String development_field,
        String phoneNum,
        boolean isNative
) {

    public static MemberCreateCommand from(final MemberCreateRequest request) {
        return MemberCreateCommand.builder()
                .email(request.email())
                .password(request.password())
                .birthDate(request.birthDate())
                .name(request.name())
                .isNative(request.isNative())
                .development_field(request.development_field())
                .phoneNum(request.phoneNum())
                .build();
    }

    public Member toEntity(final String encodedPassword) {
        return Member.builder()
                .email(email)
                .role(UserRole.ROLE_USER)
                .password(encodedPassword)
                .birthDate(LocalDate.parse(birthDate, DateTimeFormat.dateFormat))
                .name(name)
                .isNative(isNative)
                .field(development_field)
                .phoneNum(phoneNum)
                .build();
    }
}