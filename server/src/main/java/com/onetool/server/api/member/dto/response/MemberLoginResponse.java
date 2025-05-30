package com.onetool.server.api.member.dto.response;

import lombok.Builder;

public record MemberLoginResponse(
        String accessToken
) {

    @Builder
    public MemberLoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}