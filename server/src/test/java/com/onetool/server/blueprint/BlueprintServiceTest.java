package com.onetool.server.blueprint;

import com.onetool.server.blueprint.controller.BlueprintController;
import com.onetool.server.blueprint.service.BlueprintService;
import com.onetool.server.blueprint.dto.BlueprintRequest;
import com.onetool.server.blueprint.dto.BlueprintResponse;
import com.onetool.server.blueprint.dto.SearchResponse;
import com.onetool.server.global.exception.ApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigInteger;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class BlueprintServiceTest {
    @Autowired
    private BlueprintController blueprintController;
    @Autowired
    private BlueprintService blueprintService;

    @DisplayName("키워드 기반 검색이 잘되는지 확인")
    @Test
    void search_with_keyword() {
        String keyword = "마을";
        Pageable pageable = PageRequest.of(0, 5);
        Page<SearchResponse> response = blueprintService.searchNameAndCreatorWithKeyword(keyword, pageable);
        assertThat(response.getTotalElements()).isEqualTo(2);
    }

    @DisplayName("새 blueprint가 정상적으로 생성되는지 확인")
    @Test
    public void testCreateBlueprint() {
        BlueprintRequest blueprintRequest = new BlueprintRequest(
                3L,
                "대한민국 마을",
                1L,
                50000L,
                "https://s3.bucket.image.com/",
                "대한민국의 어느 마을의 청사진입니다.",
                ".exe",
                "CAD",
                BigInteger.valueOf(100),
                40000L,
                LocalDateTime.now().plusDays(10),
                "윤성원 작가",
                "https://onetool.com/download"
        );

        ApiResponse<String> response = blueprintController.createBlueprint(blueprintRequest);

        assertThat(response.getResult()).isEqualTo("상품이 정상적으로 등록되었습니다.");
    }

    @DisplayName("blueprint id를 통해 상세 정보 조회가 되는지 확인")
    @Test
    public void testGetBlueprintDetails() {
        Long blueprintId = 1L;

        ApiResponse<BlueprintResponse> response = blueprintController.getBlueprintDetails(blueprintId);

        assertThat(response.getMessage()).isEqualTo("요청에 성공하였습니다.");
    }

    @DisplayName("blueprint가 정상적으로 삭제되는지 확인")
    @Test
    public void testDeleteBlueprint() {
        Long blueprintId = 1L;

        ApiResponse<?> response = blueprintController.deleteBlueprint(blueprintId);

        assertThat(response.getMessage()).isEqualTo("요청에 성공하였습니다.");
        assertThat(response.getResult()).isEqualTo("상품이 정상적으로 삭제 되었습니다.");
    }

    @DisplayName("blueprint가 정상적으로 업데이트되는지 확인")
    @Test
    public void testUpdateBlueprint() {
        BlueprintResponse blueprintResponse = new BlueprintResponse(
                653L,
                "대한민국 마을",
                1L,
                50000L,
                "https://s3.bucket.image.com/",
                "한국의 어느 마을의 청사진입니다.",
                ".exe",
                "CAD",
                BigInteger.valueOf(100),
                40000L,
                LocalDateTime.now().plusDays(10),
                "윤성원 작가",
                "https://onetool.com/download"
        );

        ApiResponse<?> response = blueprintController.updateBlueprint(blueprintResponse);

        assertThat(response.getMessage()).isEqualTo("요청에 성공하였습니다.");
        assertThat(response.getResult()).isEqualTo("상품이 정상적으로 수정 되었습니다.");
    }
}