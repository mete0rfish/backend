package com.onetool.server.api.blueprint.service;

import com.onetool.server.api.blueprint.Blueprint;
import com.onetool.server.api.blueprint.InspectionStatus;
import com.onetool.server.api.blueprint.repository.BlueprintRepository;
import com.onetool.server.global.exception.BlueprintNullPointException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import com.onetool.server.global.exception.BlueprintNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BlueprintSearchService {

    private final BlueprintRepository blueprintRepository;

    public Blueprint findBlueprintById(Long id) {
        return blueprintRepository.findById(id)
                .orElseThrow(() -> new BlueprintNotFoundException(id.toString()));
    }

    public List<Blueprint> findAllBlueprintByBlueprintPage(Page<Blueprint> blueprintPage) {
        if (blueprintPage == null) {
            throw new BlueprintNullPointException("blueprintPage가 NULL입니다. 함수 명 : findAllBlueprintByBlueprintPage");
        }

        return blueprintRepository.findWithOrderBlueprints(blueprintPage.getContent());
    }

    public List<Blueprint> findAllBlueprintByBlueprintList(List<Blueprint> blueprints) {
        if (blueprints == null) {
            throw new BlueprintNullPointException("blueprints가 NULL입니다. 함수 명 : findAllBlueprintByOrderBlueprints");
        }

        return blueprintRepository.findWithOrderBlueprints(blueprints);
    }

    public Page<Blueprint> findAllBlueprintPage(Pageable pageable) {
        if (pageable == null) {
            throw new IllegalArgumentException("pageblae이 NULL 입니다. 함수명 : findAllBlueprint");
        }

        return blueprintRepository.findByInspectionStatus(InspectionStatus.PASSED, pageable);
    }

    public Page<Blueprint> findAllBlueprintPage(Long firstCategoryId, Pageable pageable) {
        if (firstCategoryId == null || pageable == null) {
            throw new IllegalArgumentException("firstCategoryId 또는 pageable이 NULL입니다. 함수 명 : findAllBlueprintByFirstCategory");
        }

        return blueprintRepository.findAllByFirstCategory(firstCategoryId, InspectionStatus.PASSED, pageable);
    }

    public Page<Blueprint> findAllBlueprintPage(Long firstCategoryId, String secondCategory, Pageable pageable) {
        if (firstCategoryId == null || secondCategory == null || pageable == null) {
            throw new IllegalArgumentException("firstCategoryId, secondCategory 또는 pageable이 NULL입니다. 함수 명 : findAllBlueprintBySecondCategory");
        }

        return blueprintRepository.findAllBySecondCategory(firstCategoryId, secondCategory, InspectionStatus.PASSED, pageable);
    }

    public Page<Blueprint> findAllBlueprintPage(String keyword, Pageable pageable) {
        if (keyword == null || pageable == null) {
            throw new IllegalArgumentException("keyword 또는 pageable이 NULL입니다. 함수 명 : findBlueprintPageByKeywordAndInspection");
        }

        return blueprintRepository.findAllNameAndCreatorContaining(keyword, InspectionStatus.PASSED, pageable);
    }

}