package com.onetool.server.global.auth;

public interface AuthorizationProvider {
    MemberCredential create(MemberAuthContext user);
    MemberAuthContext parseCredential(MemberCredential token);
    boolean validateToken(String token);
}
