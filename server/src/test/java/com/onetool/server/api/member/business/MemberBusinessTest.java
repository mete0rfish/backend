package com.onetool.server.api.member.business;

import com.onetool.server.api.member.domain.Member;
import com.onetool.server.api.member.dto.*;
import com.onetool.server.api.member.enums.UserRole;
import com.onetool.server.api.member.fake.FakeMemberRepository;
import com.onetool.server.api.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberBusinessTest {

    private MemberBusiness memberBusiness;
    private MemberService memberService;
    private FakeMemberRepository memberRepository;
    private PasswordEncoder encoder;

    private Member member;
    private Member admin;

    @BeforeEach
    void setup() {
        memberRepository = new FakeMemberRepository();
        memberService = new MemberService(memberRepository);
        encoder = new BCryptPasswordEncoder();
        memberBusiness = new MemberBusiness(encoder, memberService);

        admin = memberRepository.save(
                Member.builder()
                        .name("관리자 윤성원")
                        .password(encoder.encode("1234"))
                        .email("admin@example.com")
                        .role(UserRole.ROLE_ADMIN)
                        .phoneNum("01012345678")
                        .field("백엔드")
                        .isNative(true)
                        .build()
        );

        member = memberRepository.save(
                Member.builder()
                        .name("홍길동")
                        .password(encoder.encode("1234"))
                        .email("user@example.com")
                        .role(UserRole.ROLE_ADMIN)
                        .phoneNum("01000000000")
                        .field("백엔드")
                        .isNative(true)
                        .build()
        );
    }

    @Test
    void 이메일로_회원을_조회한다() {
        // given
        MemberFindEmailRequest request = new MemberFindEmailRequest("홍길동", "01000000000");

        // when
        String email = memberBusiness.findEmail(request);

        // then
        assertThat(email).isEqualTo(member.getEmail());
    }

    @Test
    void 회원의_정보를_조회한다() {
        // given
        final Long userId = member.getId();

        // when
        MemberInfoResponse response = memberBusiness.getMemberInfo(userId);

        // then
        assertThat(response.email()).isEqualTo(member.getEmail());
    }

    /*
    구매한 도면을 조회하기 위해선 주문 데이터를 생성해야 한다.
    MemberTest에서 주문 데이터를 생성하는 과정이 포함되어도 되는 것일까?
     */
    @Test
    void 회원의_구매한_도면목록을_조회한다() {
        // given
        final Long userId = member.getId();

        // when
        List<BlueprintDownloadResponse> response = memberBusiness.getPurchasedBlueprints(userId);

        // then

    }

    @Test
    void 회원을_생성한다() {
        // given
        final String email = "iamuser12@gmail.com";
        final String name = "임홍빈";

        MemberCreateRequest request = new MemberCreateRequest(
                email,
                "iamuser123",
                name,
                "2024-01-01",
                "BACKEND",
                "01034235512",
                true
        );

        // when
        MemberCreateResponse response = memberBusiness.createMember(request);

        // then
        assertThat(response.email()).isEqualTo(email);
        assertThat(response.name()).isEqualTo(name);
    }

    @Test
    void 회원의_이름을_업데이트한다() {
        // given
        final Long userId = member.getId();
        final String name = "동길홍";
        MemberUpdateRequest request = MemberUpdateRequest.builder()
                .name(name)
                .build();

        // when
        memberBusiness.updateMember(userId, request);

        // then
        String updatedName = memberRepository.findById(userId)
                .orElseThrow()
                .getName();
        assertThat(updatedName).isEqualTo(name);
    }

    @Test
    void 회원을_삭제한다() {
        // given
        final Long userId = member.getId();

        // then
        memberBusiness.deleteMember(userId);

        // when
        assertThatThrownBy(() -> memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다")))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("유저가 존재하지 않습니다");
    }
}