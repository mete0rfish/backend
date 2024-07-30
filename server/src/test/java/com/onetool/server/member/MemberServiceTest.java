package com.onetool.server.member;

import com.onetool.server.member.dto.MemberCreateResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MemberServiceTest {

    private ExtractableResponse<Response> memberCreateResponse;

    @Test
    void create_member() {
        // given
        final Map<String, Object> params = Map.of(
                "email", "admin@example.com",
                "password", "1234",
                "name", "홍길동",
                "birthDate", "2001-03-26",
                "development_field", "백엔드",
                "phoneNum", "010-0000-0000",
                "isNative", true
        );

        // when
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/users/signup")
                .then().log().all()
                .extract();

        // then
        final JsonPath result = response.jsonPath();
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(result.getString("email")).isEqualTo("admin@example.com"),
                () -> assertThat(result.getString("name")).isEqualTo("홍길동")
        );
    }

    @Test
    @DisplayName("멤버가 삭제되는지 확인")
    void memberDelete() {

        // given
        if (memberCreateResponse == null) {
            throw new IllegalStateException("회원이 생성되지 않았습니다.");
        }
        final Long userId = memberCreateResponse.jsonPath().getLong("id");

        // when
        final ExtractableResponse<Response> deleteResponse = RestAssured.given().log().all()
                .when().delete("/users/" + userId)
                .then().log().all()
                .extract();

        // then
        assertAll(
                () -> assertThat(deleteResponse.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(deleteResponse.body().asString()).isEqualTo("회원 탈퇴가 완료되었습니다.")
        );
    }
}