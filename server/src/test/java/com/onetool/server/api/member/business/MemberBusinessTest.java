package com.onetool.server.api.member.business;

import com.onetool.server.api.member.fixture.MemberFixture;
import com.onetool.server.api.member.domain.Member;
import com.onetool.server.api.member.dto.*;
import com.onetool.server.api.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
class MemberBusinessTest {

    @InjectMocks
    private MemberBusiness memberBusiness;

    @Mock
    private MemberService memberService;

    @Mock
    private PasswordEncoder encoder;

    private Member member;

    @BeforeEach
    void setup() {
        member = MemberFixture.createMember();
        member.update(encoder.encode("1234"));
    }

    @Test
    void 회원의_이메일을_조회한다() {
        // given
        MemberFindEmailRequest request = new MemberFindEmailRequest("홍길동", "01000000000");
        when(memberService.findOne(request.name(), request.phone_num())).thenReturn(member);

        // when
        String email = memberBusiness.findEmail(request);

        // then
        assertThat(email).isEqualTo(member.getEmail());
        verify(memberService, times(1)).findOne(request.name(), request.phone_num());
    }

    @Test
    void 회원의_정보를_조회한다() {
        // given
        when(memberService.findOne(member.getId())).thenReturn(member);

        // when
        MemberInfoResponse response = memberBusiness.findMemberInfo(member.getId());

        // then
        assertThat(response.email()).isEqualTo(member.getEmail());
        verify(memberService, times(1)).findOne(member.getId());
    }

    @Test
    void 회원을_생성한다() {
        // given
        MemberCreateRequest request = MemberFixture.createMemberCreateRequest();
        when(memberService.save(any(Member.class))).thenReturn(member);

        // when
        MemberCreateResponse response = memberBusiness.createMember(request);

        // then
        assertThat(response.email()).isEqualTo(member.getEmail());
        assertThat(response.name()).isEqualTo(member.getName());
        verify(memberService, times(1)).save(any(Member.class));
    }

    @Test
    void 회원의_이름을_업데이트한다() {
        // given
        MemberUpdateRequest request = MemberFixture.createMemberUpdateRequest();
        when(memberService.findOne(member.getId())).thenReturn(member);

        // when
        memberBusiness.updateMember(member.getId(), request);

        // then
        verify(memberService).save(member);
    }

    @Test
    void 회원을_삭제한다() {
        // given
        when(memberService.findOne(member.getId())).thenReturn(member);
        doNothing().when(memberService).delete(member);

        // then
        memberBusiness.deleteMember(member.getId());

        // when
        verify(memberService).delete(any(Member.class));
    }
}