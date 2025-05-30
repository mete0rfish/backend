package com.onetool.server.api.member.dto.response;

import com.onetool.server.api.member.domain.Member;
import lombok.Builder;

public record MemberCreateResponse(
        Long id,
        String email,
        String name
) {

    @Builder
    public MemberCreateResponse(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public static MemberCreateResponse of(Member member) {
        return MemberCreateResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }
}