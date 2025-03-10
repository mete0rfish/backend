package com.onetool.server.api.member.fixture;

import com.onetool.server.api.cart.Cart;
import com.onetool.server.api.member.domain.Member;
import com.onetool.server.api.member.dto.MemberCreateRequest;
import com.onetool.server.api.member.dto.MemberUpdateRequest;
import com.onetool.server.api.member.enums.UserRole;

public class MemberFixture {

    public static Member createAdmin() {
        return Member.builder()
                .id(1L)
                .name("관리자 윤성원")
                .email("admin@example.com")
                .role(UserRole.ROLE_ADMIN)
                .phoneNum("01012345678")
                .field("BACKEND")
                .isNative(true)
                .build();
    }

    public static Member createMember() {
        return Member.builder()
                .id(2L)
                .name("홍길동")
                .email("user@example.com")
                .role(UserRole.ROLE_ADMIN)
                .phoneNum("01000000000")
                .field("BACKEND")
                .isNative(true)
                .build();
    }

    public static MemberCreateRequest createMemberCreateRequest() {
        return new MemberCreateRequest(
                "user@example.com",
                "1234",
                "홍길동",
                "2024-01-01",
                "BACKEND",
                "01000000000",
                true
        );
    }

    public static MemberUpdateRequest createMemberUpdateRequest() {
        return MemberUpdateRequest.builder()
                .name("길동홍")
                .build();
    }
}
