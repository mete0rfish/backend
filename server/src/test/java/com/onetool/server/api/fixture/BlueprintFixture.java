package com.onetool.server.api.fixture;

import com.onetool.server.api.blueprint.dto.request.BlueprintRequest;
import com.onetool.server.api.blueprint.dto.request.BlueprintUpdateRequest;
import com.onetool.server.api.blueprint.dto.response.BlueprintResponse;
import com.onetool.server.api.blueprint.dto.response.SearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;

public class BlueprintFixture {

    public static BlueprintRequest createBlueprintRequest() {
        return BlueprintRequest.builder()
                .blueprintName("대한민국 마을")
                .blueprintDetails("대한민국의 어느 마을의 청사진입니다.")
                .creatorName("원툴")
                .standardPrice(50000L)
                .salePrice(50000L)
                .extension(".exe")
                .program("CAD")
                .downloadLink("https://onetool.com/download")
                .blueprintImg("https://s3.bucket.image.com/")
                .categoryId(1L)
                .saleExpiredDate(LocalDateTime.now().plusDays(10))
                .detailImage("detailImage URL")
                .build();
    }

    public static BlueprintUpdateRequest createBlueprintUpdateRequest() {
        return BlueprintUpdateRequest.builder()
                .id(3L)
                .blueprintName("대한민국 마을")
                .categoryId(1L)
                .standardPrice(50000L)
                .blueprintImg("https://s3.bucket.image.com/")
                .blueprintDetails("한국의 어느 마을의 청사진입니다.")
                .extension(".exe")
                .program("CAD")
                .hits(100L)
                .salePrice(40000L)
                .saleExpiredDate(LocalDateTime.now().plusDays(10))
                .creatorName("원툴")
                .downloadLink("https://onetool.com/download")
                .isDeleted(false)
                .detailImage("detailImage URL")
                .build();
    }

    public static List<BlueprintResponse> createBlueprintResponseList() {
         return List.of(
                BlueprintResponse.builder().id(1L).blueprintName("골프장 1인실 평면도(1)").build(),
                BlueprintResponse.builder().id(2L).blueprintName("골프장 레이아웃 평면도(1)").build()
        );
    }

    public static List<SearchResponse> createSearchResponseList() {
        return List.of(
                SearchResponse.builder().id(1L).blueprintName("골프자 1인실 평면도(1)").creatorName("원툴").categoryId(1L).secondCategory("공공").build(),
                SearchResponse.builder().id(2L).blueprintName("골프자 레이아웃 평면도(1)").creatorName("원툴").categoryId(1L).secondCategory("공공").build()
        );
    }

    public static Page<SearchResponse> createSearchResponsePage() {
        return new PageImpl<>(createSearchResponseList());
    }
}
