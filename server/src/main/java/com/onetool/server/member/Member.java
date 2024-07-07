package com.onetool.server.member;

import com.onetool.server.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String password;
    private String email;
    private String name;
    @Column(name = "birth_daye")
    private LocalDate birthDate;
    @Column(name = "phone_num")
    private String phoneNum;
    @Column(name = "user_type")
    private boolean userType;
    private String field;
    @Column(name = "is_native")
    private boolean isNative;
    @Column(name = "service_accept")
    private boolean serviceAccept;

    @Builder
    private Member(Long id, String password, String email, String name, LocalDate birthDate, String phoneNum, boolean userType, String field, boolean isNative, boolean serviceAccept) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.name = name;
        this.birthDate = birthDate;
        this.phoneNum = phoneNum;
        this.userType = userType;
        this.field = field;
        this.isNative = isNative;
        this.serviceAccept = serviceAccept;
    }

//    public static Member createMember(MemberRequest reuqest){
//        return Member.builder()
//                .
//    }
}
