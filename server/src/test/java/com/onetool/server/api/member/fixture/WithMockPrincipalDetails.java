package com.onetool.server.api.member.fixture;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockPrincipalDetailsSecurityContextFactory.class)
public @interface WithMockPrincipalDetails {
    long id() default 2L;
}
