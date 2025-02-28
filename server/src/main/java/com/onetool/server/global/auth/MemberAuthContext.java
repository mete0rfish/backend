package com.onetool.server.global.auth;

import com.onetool.server.api.member.domain.Member;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MemberAuthContext {

    private Long id;
    private String name;
    private String role;
    private String email;
    private String password;

    public static MemberAuthContext from(Member member) {
        return MemberAuthContext.builder()
                .id(member.getId())
                .role(member.getRole().name())
                .name(member.getName())
                .email(member.getEmail())
                .password(member.getPassword())
                .build();
    }
}
