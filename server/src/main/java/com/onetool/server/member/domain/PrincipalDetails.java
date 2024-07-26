package com.onetool.server.member.domain;

import com.onetool.server.global.auth.MemberAuthContext;
import com.onetool.server.member.Member;
import com.onetool.server.member.UserRole;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Getter
public class PrincipalDetails implements UserDetails, OAuth2User {

    private final MemberAuthContext context;
    private Map<String, Object> attributes;

    public PrincipalDetails(MemberAuthContext context) {
        this.context = context;
    }

    public PrincipalDetails(MemberAuthContext context, Map<String, Object> attributes) {
        this.context = context;
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_" + context.getRole());

        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return context.getPassword();
    }

    @Override
    public String getUsername() {
        return context.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return context.getEmail();
    }
}
