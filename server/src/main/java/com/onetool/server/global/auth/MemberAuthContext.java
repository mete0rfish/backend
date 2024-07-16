package com.onetool.server.global.auth;

public record MemberAuthContext(
        String name,
        String role
) {
}
