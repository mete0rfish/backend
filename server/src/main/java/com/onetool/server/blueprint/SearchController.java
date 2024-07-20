package com.onetool.server.blueprint;

import com.onetool.server.blueprint.dto.SearchResponse;
import com.onetool.server.category.FirstCategoryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.jaxb.SpringDataJaxb.PageDto;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    private BlueprintService blueprintService;

    public SearchController(BlueprintService blueprintService) {
        this.blueprintService = blueprintService;
    }

    @GetMapping("/blueprint")
    public ResponseEntity searchWithKeyword(
            @RequestParam("s")String keyword,
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC)Pageable pageable
            ) {

        Page<SearchResponse> response = blueprintService.searchNameAndCreatorWithKeyword(keyword, pageable);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/blueprint/building")
    public ResponseEntity searchBuildingCategory(
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC)Pageable pageable,
            @RequestParam(required = false) String category
    ) {
        Page<SearchResponse> responses;
        if(category.isEmpty()){
            responses = blueprintService.findAllByFirstCategory(FirstCategoryType.CATEGORY_BUILDING, pageable);
        } else {
            responses = blueprintService.findAllBySecondCategory(FirstCategoryType.CATEGORY_BUILDING, category, pageable);
        }
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/blueprint/civil")
    public ResponseEntity searchCivilCategory(
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC)Pageable pageable,
            @RequestParam(required = false) String category
    ) {
        Page<SearchResponse> responses;
        if(category.isEmpty()) {
            responses = blueprintService.findAllByFirstCategory(FirstCategoryType.CATEGORY_CIVIL, pageable);
        } else {
            responses = blueprintService.findAllBySecondCategory(FirstCategoryType.CATEGORY_CIVIL, category, pageable);
        }
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/blueprint/interior")
    public ResponseEntity searchInteriorCategory(
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC)Pageable pageable,
            @RequestParam(required = false) String category
    ) {
        Page<SearchResponse> responses;
        if(category.isEmpty()) {
            responses = blueprintService.findAllByFirstCategory(FirstCategoryType.CATEGORY_INTERIOR, pageable);
        } else {
            responses = blueprintService.findAllBySecondCategory(FirstCategoryType.CATEGORY_INTERIOR, category, pageable);
        }
        return ResponseEntity.ok().body(responses);
    }
    @GetMapping("/blueprint/machine")
    public ResponseEntity searchMachineCategory(
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC)Pageable pageable,
            @RequestParam(required = false) String category
    ) {
        Page<SearchResponse> responses;
        if(category.isEmpty()) {
            responses = blueprintService.findAllByFirstCategory(FirstCategoryType.CATEGORY_INTERIOR, pageable);
        } else {
            responses = blueprintService.findAllBySecondCategory(FirstCategoryType.CATEGORY_INTERIOR, category, pageable);
        }
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/blueprint/electric")
    public ResponseEntity searchElectricCategory(
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC)Pageable pageable,
            @RequestParam(required = false) String category
    ) {
        Page<SearchResponse> responses;
        if(category.isEmpty()) {
            responses = blueprintService.findAllByFirstCategory(FirstCategoryType.CATEGORY_INTERIOR, pageable);
        } else {
            responses = blueprintService.findAllBySecondCategory(FirstCategoryType.CATEGORY_INTERIOR, category, pageable);
        }
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/blueprint/etc")
    public ResponseEntity searchEtcCategory(
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC)Pageable pageable
    ) {
        Page<SearchResponse> responses = blueprintService.findAllByFirstCategory(FirstCategoryType.CATEGORY_ETC, pageable);
        return ResponseEntity.ok().body(responses);
    }
}
