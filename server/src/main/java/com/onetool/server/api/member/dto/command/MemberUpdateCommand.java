package com.onetool.server.api.member.dto.command;

import com.onetool.server.api.member.domain.Member;
import com.onetool.server.api.member.dto.request.MemberUpdateRequest;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public record MemberUpdateCommand(
    Long id,
    String email,
    String name,
    String phoneNum,
    String developmentField,
    String newPassword
){

    public static MemberUpdateCommand from(Long id, MemberUpdateRequest request) {
        return MemberUpdateCommand.builder()
                .id(id)
                .email(request.email())
                .name(request.name())
                .phoneNum(request.phoneNum())
                .developmentField(request.developmentField())
                .newPassword(request.newPassword())
                .build();
    }
}