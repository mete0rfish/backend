package com.onetool.server.api.blueprint.service;

import com.onetool.server.api.blueprint.Blueprint;
import com.onetool.server.api.blueprint.dto.BlueprintRequest;
import com.onetool.server.api.blueprint.dto.BlueprintResponse;
import com.onetool.server.api.blueprint.dto.BlueprintSortRequest;
import com.onetool.server.api.blueprint.dto.SearchResponse;
import com.onetool.server.api.blueprint.enums.SortType;
import com.onetool.server.api.blueprint.repository.BlueprintRepository;
import com.onetool.server.api.blueprint.InspectionStatus;
import com.onetool.server.api.category.FirstCategoryType;
import com.onetool.server.global.exception.BlueprintNotApprovedException;
import com.onetool.server.global.exception.BlueprintNotFoundException;
import com.onetool.server.global.exception.InvalidSortTypeException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlueprintService {

    private BlueprintRepository blueprintRepository;

    public BlueprintService(BlueprintRepository blueprintRepository) {
        this.blueprintRepository = blueprintRepository;
    }

    public boolean createBlueprint(final BlueprintRequest blueprintRequest) {
        Blueprint blueprint = convertToBlueprint(blueprintRequest);
        saveBlueprint(blueprint);
        return true;
    }

    public BlueprintResponse findApprovedBlueprintById(Long id) {
        Blueprint blueprint = blueprintRepository.findById(id)
                .orElseThrow(BlueprintNotFoundException::new);

        if (blueprint.getInspectionStatus() != InspectionStatus.PASSED) {
            throw new BlueprintNotApprovedException();
        }

        return BlueprintResponse.from(blueprint);
    }

    @Transactional
    public Blueprint saveBlueprint(Blueprint blueprint) {
        return blueprintRepository.save(blueprint);
    }

    @Transactional
    public boolean updateBlueprint(BlueprintResponse blueprintResponse) {
        Blueprint existingBlueprint = blueprintRepository.findById(blueprintResponse.id())
                .orElseThrow(BlueprintNotFoundException::new);
        Blueprint updatedBlueprint = updateExistingBlueprint(existingBlueprint, blueprintResponse);

        saveBlueprint(updatedBlueprint);
        return true;
    }

    @Transactional
    public boolean deleteBlueprint(Long id) {
        blueprintRepository.findById(id)
                .orElseThrow(BlueprintNotFoundException::new);

        blueprintRepository.deleteById(id);
        return true;
    }

    public List<BlueprintResponse> sortBlueprintsByCategory(BlueprintSortRequest blueprintSortRequest, Pageable pageable) {
        SortType sortType = SortType.valueOf(blueprintSortRequest.sortBy().toUpperCase());
        String sortOrder = blueprintSortRequest.sortOrder();
        Sort sort = getSortBySortType(sortType, sortOrder);

        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        if (blueprintSortRequest.categoryId() == null) {
            Page<Blueprint> blueprintPage = blueprintRepository.findByInspectionStatus(InspectionStatus.PASSED, sortedPageable);

            return blueprintPage.stream()
                    .map(BlueprintResponse::from)
                    .collect(Collectors.toList());
        }

        Page<Blueprint> blueprintPage = blueprintRepository.findByCategoryIdAndStatus(
                blueprintSortRequest.categoryId(),
                InspectionStatus.PASSED,
                sortedPageable
        );

        return blueprintPage.stream()
                .map(BlueprintResponse::from)
                .collect(Collectors.toList());
    }

    public Page<SearchResponse> searchNameAndCreatorWithKeyword(String keyword, Pageable pageable) {
        Page<Blueprint> result = blueprintRepository.findAllNameAndCreatorContaining(keyword, InspectionStatus.PASSED, pageable);
        List<SearchResponse> list = result.getContent().stream()
                .map(SearchResponse::from)
                .collect(Collectors.toList());
        return new PageImpl<>(list, pageable, result.getTotalElements());
    }

    public Page<SearchResponse> findAllByCategory(FirstCategoryType firstCategory, String secondCategory, Pageable pageable) {
        if(secondCategory == null) {
            return findAllByFirstCategory(firstCategory, pageable);
        }
        return findAllBySecondCategory(firstCategory, secondCategory, pageable);
    }

    public Page<SearchResponse> findAll(Pageable pageable) {
        Page<Blueprint> result = blueprintRepository.findByInspectionStatus(InspectionStatus.PASSED, pageable);
        List<SearchResponse> list = result.getContent().stream()
                .map(SearchResponse::from)
                .collect(Collectors.toList());
        return new PageImpl<>(list, pageable, result.getTotalElements());
    }

    private Page<SearchResponse> findAllByFirstCategory(FirstCategoryType category, Pageable pageable) {
        Page<Blueprint> result = blueprintRepository.findAllByFirstCategory(category.getType(), InspectionStatus.PASSED, pageable);
        List<SearchResponse> list = result.getContent().stream()
                .map(SearchResponse::from)
                .collect(Collectors.toList());
        return new PageImpl<>(list, pageable, result.getTotalElements());
    }

    private Page<SearchResponse> findAllBySecondCategory(FirstCategoryType firstCategory, String secondCategory, Pageable pageable) {
        Page<Blueprint> result = blueprintRepository.findAllBySecondCategory(
                firstCategory.getType(), secondCategory, InspectionStatus.PASSED, pageable);
        List<SearchResponse> list = result.getContent().stream()
                .map(SearchResponse::from)
                .collect(Collectors.toList());
        return new PageImpl<>(list, pageable, result.getTotalElements());
    }

    private Blueprint convertToBlueprint(final BlueprintRequest blueprintRequest) {
        return Blueprint.fromRequest(blueprintRequest);
    }

    private Blueprint updateExistingBlueprint(Blueprint existingBlueprint, BlueprintResponse blueprintResponse) {
        return Blueprint.builder()
                .id(existingBlueprint.getId())
                .blueprintName(blueprintResponse.blueprintName())
                .categoryId(blueprintResponse.categoryId())
                .standardPrice(blueprintResponse.standardPrice())
                .blueprintImg(blueprintResponse.blueprintImg())
                .blueprintDetails(blueprintResponse.blueprintDetails())
                .extension(blueprintResponse.extension())
                .program(blueprintResponse.program())
                .hits(blueprintResponse.hits())
                .salePrice(blueprintResponse.salePrice())
                .saleExpiredDate(blueprintResponse.saleExpiredDate())
                .creatorName(blueprintResponse.creatorName())
                .downloadLink(blueprintResponse.downloadLink())
                .build();
    }

    private Sort getSortBySortType(SortType sortType, String sortOrder) {
        Sort.Direction direction = Sort.Direction.ASC;

        if ("desc".equalsIgnoreCase(sortOrder)) {
            direction = Sort.Direction.DESC;
        }

        if (sortType == SortType.PRICE) {
            return Sort.by(
                    Sort.Order.by("salePrice").with(direction).nullsLast(),
                    Sort.Order.by("standardPrice").with(direction).nullsLast()
            );
        }

        if (sortType == SortType.CREATED_AT) {
            return Sort.by(Sort.Order.by("createdAt").with(direction).nullsLast());
        }

        if (sortType == SortType.EXTENSION) {
            return Sort.by(Sort.Order.by("extension").with(direction).nullsLast());
        }

        throw new InvalidSortTypeException();
    }
    private Double getPrice(Blueprint blueprint) {
        if (blueprint.getSalePrice() != null
                && blueprint.getSaleExpiredDate() != null
                && blueprint.getSaleExpiredDate().isAfter(LocalDateTime.now())) {
            return blueprint.getSalePrice().doubleValue();
        }

        if (blueprint.getStandardPrice() != null) {
            return blueprint.getStandardPrice().doubleValue();
        }

        return 0.0;
    }

    public boolean isValidSortType(String sortBy) {
        return Arrays.stream(SortType.values())
                .anyMatch(sortType -> sortType.name().equalsIgnoreCase(sortBy));
    }
}