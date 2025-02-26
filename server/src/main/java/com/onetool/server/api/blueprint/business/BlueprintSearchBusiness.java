package com.onetool.server.api.blueprint.business;

import com.onetool.server.api.blueprint.Blueprint;
import com.onetool.server.api.blueprint.InspectionStatus;
import com.onetool.server.api.blueprint.dto.response.BlueprintResponse;
import com.onetool.server.api.blueprint.dto.response.BlueprintSortRequest;
import com.onetool.server.api.blueprint.dto.response.SearchResponse;
import com.onetool.server.api.blueprint.enums.SortType;
import com.onetool.server.api.blueprint.service.BlueprintSearchService;
import com.onetool.server.api.category.FirstCategoryType;
import com.onetool.server.global.annotation.Business;
import com.onetool.server.global.exception.BlueprintNotApprovedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;

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
        List<SearchResponse> searchResponseList = SearchResponse.toSearchResponseList(withCartBlueprints);

        return new PageImpl<>(searchResponseList, pageable, blueprintPage.getTotalElements());
    }

    @Transactional
    public Page<SearchResponse> getSearchResponsePageAllCategory(FirstCategoryType firstCategory, String secondCategory, Pageable pageable) {
        Page<Blueprint> blueprintPage = (secondCategory == null)
                ? blueprintSearchService.findAllBlueprintByFirstCategory(firstCategory.getCategoryId(), pageable)
                : blueprintSearchService.findAllBlueprintBySecondCategory(firstCategory.getCategoryId(), secondCategory, pageable);

        List<SearchResponse> searchResponseList = SearchResponse.toSearchResponseList(blueprintPage.getContent());

        return new PageImpl<>(searchResponseList, pageable, blueprintPage.getTotalElements());
    }

    @Transactional
    public Page<SearchResponse> getSearchResponsePage(Pageable pageable) {
        Page<Blueprint> blueprintPage = blueprintSearchService.findAllBlueprint(pageable);
        List<SearchResponse> searchResponseList = SearchResponse.toSearchResponseList(blueprintPage.getContent());

        return new PageImpl<>(searchResponseList, pageable, blueprintPage.getTotalElements());
    }

    @Transactional
    public BlueprintResponse getApprovedBlueprintResponse(Long blueprintId) {
        Blueprint blueprint = blueprintSearchService.findBlueprintById(blueprintId);
        if (blueprint.getInspectionStatus() != InspectionStatus.PASSED) {
            throw new BlueprintNotApprovedException(blueprintId.toString());
        }

        return BlueprintResponse.from(blueprint);
    }

    @Transactional
    public List<BlueprintResponse> getSortedBluePrintList(BlueprintSortRequest request, Pageable sortedPageable) {
        Long categoryId = getCategoryId(request.categoryName());
        FirstCategoryType category = (categoryId != null) ? FirstCategoryType.findByCategoryId(categoryId) : null;
        Page<Blueprint> blueprintPage = (category == null)
                ? blueprintSearchService.findAllBlueprintPage(sortedPageable)
                : blueprintSearchService.findAllBlueprintByFirstCategory(category.getCategoryId(), sortedPageable);

        return BlueprintResponse.toBlueprintResponseList(blueprintPage.getContent());
    }

    private Long getCategoryId(String categoryName) {
        if (categoryName == null) {
            return null;
        }
        return FirstCategoryType.findByType(categoryName).getCategoryId();
    }
}
