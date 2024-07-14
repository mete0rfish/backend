package com.onetool.server.blueprint;

import com.onetool.server.blueprint.dto.SearchResponse;
import com.onetool.server.category.FirstCategoryType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

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

    @DisplayName("building 카테고리를 가진 도면이 잘 나오는지 확인")
    @Test
    void search_first_category_building() {
        FirstCategoryType type = FirstCategoryType.CATEGORY_BUILDING;

        Pageable pageable = PageRequest.of(0, 5);
        Page<SearchResponse> response = blueprintService.findAllByFirstCategory(type, pageable);
        assertThat(response.getTotalElements()).isEqualTo(1);
    }

    @DisplayName("blueprint id를 통해 상세 정보 조회가 되는지 확인")
    @Test
    public void testGetBlueprintDetails() {
        Long blueprintId = 1L;

        ResponseEntity<Blueprint> response = blueprintController.getBlueprintDetails(blueprintId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        Blueprint responseBody = response.getBody();
        assertThat(responseBody.getBlueprintName()).isEqualTo("대한민국 마을");
        assertThat(responseBody.getBlueprintDetails()).isEqualTo("대한민국의 어느 마을의 청사진입니다.");
        assertThat(responseBody.getCreatorName()).isEqualTo("윤성원 작가");
        assertThat(responseBody.getStandardPrice()).isEqualTo(50000L);
        assertThat(responseBody.getSalePrice()).isEqualTo(40000L);
        assertThat(responseBody.getProgram()).isEqualTo("CAD");
        assertThat(responseBody.getDownloadLink()).isEqualTo("https://onetool.com/download");
        assertThat(responseBody.getExtension()).isEqualTo(".exe");
        assertThat(responseBody.getBlueprintImg()).isEqualTo("https://s3.bucket.image.com/");
        assertThat(responseBody.getCategoryId()).isEqualTo(1L);
    }
}