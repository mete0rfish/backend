package com.onetool.server.api.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetool.server.api.helper.MockBeanInjection;
import com.onetool.server.api.member.domain.Member;
import com.onetool.server.api.member.dto.command.MemberUpdateCommand;
import com.onetool.server.api.member.dto.request.MemberCreateRequest;
import com.onetool.server.api.member.dto.response.MemberCreateResponse;
import com.onetool.server.api.member.dto.response.MemberInfoResponse;
import com.onetool.server.api.member.dto.request.MemberUpdateRequest;
import com.onetool.server.api.member.fixture.MemberFixture;
import com.onetool.server.api.member.fixture.WithMockPrincipalDetails;
import com.onetool.server.global.auth.MemberAuthContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureMockMvc
@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest extends MockBeanInjection {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Member member;

    @BeforeEach
    void setup() {
        member = MemberFixture.createMember();
    }

    @Test
    @WithMockUser(username = "test@gmail.com", password = "0000")
    void 회원을_생성한다() throws Exception {
        // given
        MemberCreateRequest request = MemberFixture.createMemberCreateRequest();
        MemberCreateResponse response = MemberFixture.createMemberCreateResponse();
        when(memberBusiness.createMember(request)).thenReturn(response);

        // when
        ResultActions resultActions =
                mockMvc.perform(post("/users/signup")
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("result.email").value("user@example.com"));
    }

    @Test
    @WithMockPrincipalDetails(id = 2L)
    void 회원_정보를_업데이트한다() throws Exception {
        // Given
        MemberUpdateRequest request = MemberFixture.createMemberUpdateRequest();
        String jsonRequest = objectMapper.writeValueAsString(request);

        doNothing().when(memberBusiness).updateMember(Mockito.any(MemberUpdateCommand.class));

        // When
        ResultActions result = mockMvc.perform(
                patch("/users")
                        .content(jsonRequest)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // Then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("회원 정보가 수정되었습니다."));
    }

    @Test
    @WithMockPrincipalDetails(id = 2L)
    void 회원을_삭제한다() throws Exception {
        // Given
        doNothing().when(memberBusiness).deleteMember(Mockito.anyLong());

        // When
        ResultActions result = mockMvc.perform(
                delete("/users")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // Then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("회원 탈퇴가 완료되었습니다."));
    }

    @Test
    @WithMockPrincipalDetails(id = 2L)
    void 회원_정보를_조회한다() throws Exception{
        // Given
        when(memberBusiness.findMemberInfo(Mockito.anyLong())).thenReturn(MemberInfoResponse.from(member));

        // When
        ResultActions result = mockMvc.perform(
                get("/users")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // Then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.id").value(2))
                .andExpect(jsonPath("$.result.email").value(member.getEmail()))
                .andExpect(jsonPath("$.result.name").value(member.getName()));
    }

    @Test
    @WithMockPrincipalDetails(id = 2L)
    void 회원의_문의내역을_조회한다() throws Exception {
        // Given
        when(qnaBoardBusiness.getMyQna(any(MemberAuthContext.class)))
                .thenReturn(MemberFixture.createQnaBoardBriefResponses());

        // When
        ResultActions result = mockMvc.perform(
                get("/users/myQna")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // Then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.length()").value(2))
                .andExpect(jsonPath("$.result[0].title").value("test1"))
                .andExpect(jsonPath("$.result[0].writer").value("writer1"))
                .andExpect(jsonPath("$.result[1].title").value("test2"))
                .andExpect(jsonPath("$.result[1].writer").value("writer2"));
    }

    @Test
    @WithMockPrincipalDetails(id = 2L)
    void 회원의_구매내역을_조회한다() throws Exception {
        // Given
        when(memberBusiness.findPurchasedBlueprints(any(Long.class)))
                .thenReturn(MemberFixture.createBlueprintDownloadResponses());

        // When
        ResultActions result = mockMvc.perform(
                get("/users/myPurchase")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // Then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.length()").value(2))
                .andExpect(jsonPath("$.result[0].blueprintName").value("도면1"))
                .andExpect(jsonPath("$.result[0].blueprintId").value("1"))
                .andExpect(jsonPath("$.result[1].blueprintName").value("도면2"))
                .andExpect(jsonPath("$.result[1].blueprintId").value("2"));
    }
}