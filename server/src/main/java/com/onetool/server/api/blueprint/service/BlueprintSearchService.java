package com.onetool.server.api.blueprint.service;

import com.onetool.server.api.blueprint.Blueprint;
import com.onetool.server.api.blueprint.InspectionStatus;
import com.onetool.server.api.blueprint.dto.response.BlueprintResponse;
import com.onetool.server.api.blueprint.dto.response.BlueprintSortRequest;
import com.onetool.server.api.blueprint.dto.response.SearchResponse;
import com.onetool.server.api.blueprint.enums.SortType;
import com.onetool.server.api.blueprint.repository.BlueprintRepository;
import com.onetool.server.api.category.FirstCategoryType;
import com.onetool.server.global.exception.BlueprintNotApprovedException;
import com.onetool.server.global.exception.BlueprintNullPointException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import com.onetool.server.global.exception.BlueprintNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BlueprintSearchService {

    private final BlueprintRepository blueprintRepository;

    public BlueprintResponse findApprovedBlueprintById(Long id) {
        Blueprint blueprint = blueprintRepository.findById(id)
                .orElseThrow(() -> new BlueprintNotFoundException(id.toString()));

        if (blueprint.getInspectionStatus() != InspectionStatus.PASSED) {
            throw new BlueprintNotApprovedException(id.toString());
        }

        return BlueprintResponse.from(blueprint);
    }

    public List<BlueprintResponse> sortBlueprints(BlueprintSortRequest request, Pageable pageable) {
        Long categoryId = getCategoryId(request.categoryName());
        Sort sort = SortType.getSortBySortType(SortType.valueOf(request.sortBy().toUpperCase()), request.sortOrder());
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        if (categoryId == null) {
            return sortBlueprintsWithoutCategory(sortedPageable);
        }

        return sortBlueprintsWithCategory(categoryId, sortedPageable);
    }

    private List<BlueprintResponse> sortBlueprintsWithoutCategory(Pageable sortedPageable) {
        Page<Blueprint> blueprintPage = blueprintRepository.findByInspectionStatus(InspectionStatus.PASSED, sortedPageable);
        return blueprintPage.stream()
                .map(BlueprintResponse::from)
                .collect(Collectors.toList());
    }

    private List<BlueprintResponse> sortBlueprintsWithCategory(Long categoryId, Pageable sortedPageable) {
        FirstCategoryType category = FirstCategoryType.findByCategoryId(categoryId);
        Page<Blueprint> blueprintPage = blueprintRepository.findAllByFirstCategory(
                category.getCategoryId(), InspectionStatus.PASSED, sortedPageable);
        return blueprintPage.stream()
                .map(BlueprintResponse::from)
                .collect(Collectors.toList());
    }

    public Page<Blueprint> findBlueprintPageByKeywordAndInspection(String keyword, Pageable pageable) {
        if (keyword == null || pageable == null) {
            throw new IllegalArgumentException("keyword 또는 pageable이 NULL입니다. 함수 명 : findBlueprintPageByKeywordAndInspection");
        }

        return blueprintRepository.findAllNameAndCreatorContaining(keyword, InspectionStatus.PASSED, pageable);
    }

    public List<Blueprint> findAllBlueprintByBlueprintPage(Page<Blueprint> blueprintPage) {
        if (blueprintPage == null) {
            throw new BlueprintNullPointException("blueprintPage가 NULL입니다. 함수 명 : findAllBlueprintByBlueprintPage");
        }

        return blueprintRepository.findWithOrderBlueprints(blueprintPage.getContent());
    }

    public List<Blueprint> findAllBlueprintByOrderBlueprints(List<Blueprint> blueprints) {
        if (blueprints == null) {
            throw new BlueprintNullPointException("blueprints가 NULL입니다. 함수 명 : findAllBlueprintByOrderBlueprints");
        }

        return blueprintRepository.findWithOrderBlueprints(blueprints);
    }

    public Page<SearchResponse> findAll(Pageable pageable) {
        Page<Blueprint> result = blueprintRepository.findByInspectionStatus(InspectionStatus.PASSED, pageable);
        List<SearchResponse> list = SearchResponse.fromBlueprintsToSearchResponseList(result.getContent());
        return new PageImpl<>(list, pageable, result.getTotalElements());
    }

    public Page<Blueprint> findAllBlueprintByFirstCategory(Long firstCategoryId, Pageable pageable) {
        if (firstCategoryId == null || pageable == null) {
            throw new IllegalArgumentException("firstCategoryId 또는 pageable이 NULL입니다. 함수 명 : findAllBlueprintByFirstCategory");
        }

        return blueprintRepository.findAllByFirstCategory(firstCategoryId, InspectionStatus.PASSED, pageable);
    }

    public Page<Blueprint> findAllBlueprintBySecondCategory(Long firstCategoryId, String secondCategory, Pageable pageable) {
        if (firstCategoryId == null || secondCategory == null || pageable == null) {
            throw new IllegalArgumentException("firstCategoryId, secondCategory 또는 pageable이 NULL입니다. 함수 명 : findAllBlueprintBySecondCategory");
        }

        return blueprintRepository.findAllBySecondCategory(firstCategoryId, secondCategory, InspectionStatus.PASSED, pageable);
    }


    private Long getCategoryId(String categoryName) {
        if (categoryName == null) {
            return null;
        }
        return FirstCategoryType.findByType(categoryName).getCategoryId();
    }
}