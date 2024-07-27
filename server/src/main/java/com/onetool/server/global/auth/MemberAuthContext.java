package com.onetool.server.global.auth;

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
}
