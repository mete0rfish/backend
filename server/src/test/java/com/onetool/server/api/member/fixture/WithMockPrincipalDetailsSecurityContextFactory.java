package com.onetool.server.api.member.fixture;

import com.onetool.server.global.auth.MemberAuthContext;
import com.onetool.server.global.auth.login.PrincipalDetails;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockPrincipalDetailsSecurityContextFactory
implements WithSecurityContextFactory<WithMockPrincipalDetails> {

    @Override
    public SecurityContext createSecurityContext(WithMockPrincipalDetails annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        // PrincipalDetails 및 MemberAuthContext Mock 객체 생성
        PrincipalDetails principalDetails = Mockito.mock(PrincipalDetails.class);
        MemberAuthContext authContext = Mockito.mock(MemberAuthContext.class);

        Mockito.when(principalDetails.getContext()).thenReturn(authContext);
        Mockito.when(authContext.getId()).thenReturn(annotation.id());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principalDetails, null, principalDetails.getAuthorities()
        );

        context.setAuthentication(authentication);
        return context;
    }
}
