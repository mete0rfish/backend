package com.onetool.server.blueprint.controller;

import com.onetool.server.blueprint.service.BlueprintService;
import com.onetool.server.blueprint.dto.SearchResponse;
import com.onetool.server.category.FirstCategoryType;
import com.onetool.server.global.exception.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    private BlueprintService blueprintService;

    public SearchController(BlueprintService blueprintService) {
        this.blueprintService = blueprintService;
    }

    @GetMapping("/blueprint")
    public ApiResponse<?> searchWithKeyword(
            @RequestParam("s")String keyword,
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC)Pageable pageable
    ) {

        Page<SearchResponse> response = blueprintService.searchNameAndCreatorWithKeyword(keyword, pageable);
        return ApiResponse.onSuccess(response);
    }

    @GetMapping("/blueprint/building")
    public ApiResponse<?> searchBuildingCategory(
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC)Pageable pageable,
            @RequestParam(required = false) String category
    ) {
        Page<SearchResponse> responses;
        if(category == null){
            responses = blueprintService.findAllByFirstCategory(FirstCategoryType.CATEGORY_BUILDING, pageable);
        } else {
            responses = blueprintService.findAllBySecondCategory(FirstCategoryType.CATEGORY_BUILDING, category, pageable);
        }
        return ApiResponse.onSuccess(responses);
    }

    @GetMapping("/blueprint/civil")
    public ApiResponse<?> searchCivilCategory(
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC)Pageable pageable,
            @RequestParam(required = false) String category
    ) {
        Page<SearchResponse> responses;
        if(category == null) {
            responses = blueprintService.findAllByFirstCategory(FirstCategoryType.CATEGORY_CIVIL, pageable);
        } else {
            responses = blueprintService.findAllBySecondCategory(FirstCategoryType.CATEGORY_CIVIL, category, pageable);
        }
        return ApiResponse.onSuccess(responses);
    }

    @GetMapping("/blueprint/interior")
    public ApiResponse<?> searchInteriorCategory(
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC)Pageable pageable,
            @RequestParam(required = false) String category
    ) {
        Page<SearchResponse> responses;
        if(category == null) {
            responses = blueprintService.findAllByFirstCategory(FirstCategoryType.CATEGORY_INTERIOR, pageable);
        } else {
            responses = blueprintService.findAllBySecondCategory(FirstCategoryType.CATEGORY_INTERIOR, category, pageable);
        }
        return ApiResponse.onSuccess(responses);
    }
    @GetMapping("/blueprint/machine")
    public ApiResponse<?> searchMachineCategory(
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC)Pageable pageable,
            @RequestParam(required = false) String category
    ) {
        Page<SearchResponse> responses;
        if(category == null) {
            responses = blueprintService.findAllByFirstCategory(FirstCategoryType.CATEGORY_INTERIOR, pageable);
        } else {
            responses = blueprintService.findAllBySecondCategory(FirstCategoryType.CATEGORY_INTERIOR, category, pageable);
        }
        return ApiResponse.onSuccess(responses);
    }

    @GetMapping("/blueprint/electric")
    public ApiResponse<?> searchElectricCategory(
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC)Pageable pageable,
            @RequestParam(required = false) String category
    ) {
        Page<SearchResponse> responses;
        if(category == null) {
            responses = blueprintService.findAllByFirstCategory(FirstCategoryType.CATEGORY_INTERIOR, pageable);
        } else {
            responses = blueprintService.findAllBySecondCategory(FirstCategoryType.CATEGORY_INTERIOR, category, pageable);
        }
        return ApiResponse.onSuccess(responses);
    }

    @GetMapping("/blueprint/etc")
    public ApiResponse<?> searchEtcCategory(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)Pageable pageable
    ) {
        Page<SearchResponse> responses = blueprintService.findAllByFirstCategory(FirstCategoryType.CATEGORY_ETC, pageable);
        return ApiResponse.onSuccess(responses);
    }

    @GetMapping("/blueprint/all")
    public ApiResponse<?> searchAllBlueprint(
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC)Pageable pageable
    ) {
        Page<SearchResponse> responses = blueprintService.findAll(pageable);
        return ApiResponse.onSuccess(responses);
    }
}