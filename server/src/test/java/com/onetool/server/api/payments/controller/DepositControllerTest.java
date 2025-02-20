package com.onetool.server.api.payments.controller;

import com.google.gson.Gson;
import com.onetool.server.api.order.controller.OrderController;
import com.onetool.server.api.order.service.OrderFixture;
import com.onetool.server.api.order.service.OrderService;
import com.onetool.server.api.payments.dto.DepositRequest;
import com.onetool.server.global.auth.MemberAuthContext;
import com.onetool.server.global.auth.jwt.JwtUtil;
import com.onetool.server.global.auth.login.PrincipalDetails;
import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;

import static com.onetool.server.api.order.service.OrderFixture.ORDER_REQUEST;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureJsonTesters
@WebMvcTest(controllers = DepositController.class)
class DepositControllerTest {

    private final DepositRequest depositRequest = DepositRequest.builder()
            .orderId(1L)
            .accountName("홍길동")
            .accountNumber("32144-4141323")
            .bankName("국민")
            .totalPrice(10000L)
            .build();

    @MockBean
    private DepositController depositController;

    @MockBean
    private OrderController orderController;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

        MemberAuthContext authContext = new MemberAuthContext(1L, "admin", "ROLE_ADMIN", "admin@example.com", "1234");
        PrincipalDetails principalDetails = new PrincipalDetails(authContext);

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        Authentication authentication = new TestingAuthenticationToken(principalDetails, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void deletePayment() throws Exception {

        // given
        ResultActions createOrderResultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/orders")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(ORDER_REQUEST))
        );
        MvcResult createOrderResult = createOrderResultActions.andReturn();
        Long orderId = Long.valueOf(createOrderResult.getResponse().getContentAsString());

        // when
        ResultActions deletePaymentResultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete("/payments/deposit")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(depositRequest))
        );

        // then
        MvcResult deletePaymentResult = deletePaymentResultActions
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }
}