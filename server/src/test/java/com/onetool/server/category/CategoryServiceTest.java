package com.onetool.server.category;

import com.onetool.server.blueprint.BlueprintService;
import com.onetool.server.blueprint.dto.SearchResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class CategoryServiceTest {

    @Autowired
    private BlueprintService blueprintService;

    @DisplayName("building 카테고리를 가진 도면이 잘 나오는지 확인")
    @Test
    void search_first_category_building() {
        FirstCategoryType type = FirstCategoryType.CATEGORY_BUILDING;

        Pageable pageable = PageRequest.of(0, 5);
        Page<SearchResponse> response = blueprintService.findAllByFirstCategory(type, pageable);
        assertThat(response.getTotalElements()).isEqualTo(1);
    }
}
