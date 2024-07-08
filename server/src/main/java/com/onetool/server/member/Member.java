package com.onetool.server.member;

import com.onetool.server.cart.Cart;
import com.onetool.server.global.entity.BaseEntity;
import com.onetool.server.qna.QnaBoard;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String password;

    @NotNull @Size(min = 1, max = 50, message = "이메일은 1 ~ 50자 이여야 합니다.") @Email
    private String email;

    @NotNull(message = "이름은 null 일 수 없습니다.") @Size(min = 1, max = 10, message = "이름은 1 ~ 10자 이여야 합니다.")
    private String name;

    @Column(name = "birth_daye") @Past
    private LocalDate birthDate;

    @Column(name = "phone_num") @NotNull @Size(min = 10, max = 11)
    private String phoneNum;

    @Column(name = "role") @ColumnDefault("'회원'")
    private  String role;

    @ColumnDefault("'기타'")
    private String field;

    @Column(name = "is_native")
    private boolean isNative;

    @Column(name = "service_accept")
    private boolean serviceAccept;

    @Column(name = "platform_type") @NotNull
    private String platformType;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<QnaBoard> qnaBoards = new ArrayList<>();

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Cart cart;

    @Builder
    private Member(String password, String email, String name, LocalDate birthDate, String phoneNum, String role, String field, boolean isNative, boolean serviceAccept, String platformType, List<QnaBoard> qnaBoards, Cart cart) {
        this.password = password;
        this.email = email;
        this.name = name;
        this.birthDate = birthDate;
        this.phoneNum = phoneNum;
        this.role = role;
        this.field = field;
        this.isNative = isNative;
        this.serviceAccept = serviceAccept;
        this.platformType = platformType;
        this.qnaBoards = qnaBoards;
        this.cart = cart;
    }
    // TODO builder패턴 완성하기
    /*    public static Member createMember(MemberRequest reuqest){
            return Member.builder()
                    .
        }
    */
}
