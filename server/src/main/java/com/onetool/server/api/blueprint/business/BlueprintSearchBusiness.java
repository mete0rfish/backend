package com.onetool.server.api.blueprint.business;

import com.onetool.server.api.blueprint.Blueprint;
import com.onetool.server.api.blueprint.dto.response.SearchResponse;
import com.onetool.server.api.blueprint.service.BlueprintSearchService;
import com.onetool.server.api.category.FirstCategoryType;
import com.onetool.server.global.annotation.Business;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Business
@RequiredArgsConstructor
public class BlueprintSearchBusiness {

    private final BlueprintSearchService blueprintSearchService;

    @Transactional
    public Page<SearchResponse> getSearchResponsePageWithKeyWordAndCreator(String keyword, Pageable pageable) {
        Page<Blueprint> blueprintPage = blueprintSearchService.findBlueprintPageByKeywordAndInspection(keyword, pageable);
        List<Blueprint> withOrderBlueprints = blueprintSearchService.findAllBlueprintByBlueprintPage(blueprintPage);
        List<Blueprint> withCartBlueprints = blueprintSearchService.findAllBlueprintByOrderBlueprints(withOrderBlueprints);
        List<SearchResponse> searchResponseList = SearchResponse.fromBlueprintsToSearchResponseList(withCartBlueprints);

        return new PageImpl<>(searchResponseList, pageable, blueprintPage.getTotalElements());
    }

    @Transactional
    public Page<SearchResponse> getSearchResponsePageAllCategory(FirstCategoryType firstCategory, String secondCategory, Pageable pageable) {
        Page<Blueprint> blueprintPage = (secondCategory == null)
                ? blueprintSearchService.findAllBlueprintByFirstCategory(firstCategory.getCategoryId(), pageable)
                : blueprintSearchService.findAllBlueprintBySecondCategory(firstCategory.getCategoryId(), secondCategory, pageable);

        List<SearchResponse> searchResponseList = SearchResponse.fromBlueprintsToSearchResponseList(blueprintPage.getContent());

        return new PageImpl<>(searchResponseList, pageable, blueprintPage.getTotalElements());
    }
}
