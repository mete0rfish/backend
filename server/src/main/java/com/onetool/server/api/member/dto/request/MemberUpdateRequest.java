package com.onetool.server.api.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
public record MemberUpdateRequest (
    String email,
    String name,
    String phoneNum,
    String developmentField,
    String newPassword
){
}