package com.onetool.server.api.blueprint.service;

import com.onetool.server.api.blueprint.Blueprint;
import com.onetool.server.api.blueprint.InspectionStatus;
import com.onetool.server.api.blueprint.repository.BlueprintRepository;
import com.onetool.server.global.exception.BlueprintNullPointException;
import com.onetool.server.global.new_exception.exception.ApiException;
import com.onetool.server.global.new_exception.exception.error.BlueprintErrorCode;
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
                .orElseThrow(() -> new ApiException(BlueprintErrorCode.NOT_FOUND_ERROR,"blueprintId : "+id));
    }

    public List<Blueprint> findAllBlueprintByBlueprintPage(Page<Blueprint> blueprintPage) {
        if (blueprintPage == null) {
            throw new ApiException(BlueprintErrorCode.NULL_POINT_ERROR,"Blueprint가 NULL입니다.");
        }

        return blueprintRepository.findWithOrderBlueprints(blueprintPage.getContent());
    }

    public List<Blueprint> findAllBlueprintByBlueprintList(List<Blueprint> blueprints) {
        if (blueprints == null) {
            throw new ApiException(BlueprintErrorCode.NULL_POINT_ERROR,"blueprints가 NULL입니다.");
        }

        return blueprintRepository.findWithOrderBlueprints(blueprints);
    }

    public Page<Blueprint> findAllBlueprintPage(Pageable pageable) {
        if (pageable == null) {
            throw new ApiException(BlueprintErrorCode.NULL_POINT_ERROR,"pageable이 NULL입니다.");
        }

        return blueprintRepository.findByInspectionStatus(InspectionStatus.PASSED, pageable);
    }

    public Page<Blueprint> findAllBlueprintPage(Long firstCategoryId, Pageable pageable) {
        if (firstCategoryId == null || pageable == null) {
            throw new ApiException(BlueprintErrorCode.NULL_POINT_ERROR,"요청한 객체가 NULL입니다.");
        }

        return blueprintRepository.findAllByFirstCategory(firstCategoryId, InspectionStatus.PASSED, pageable);
    }

    public Page<Blueprint> findAllBlueprintPage(Long firstCategoryId, String secondCategory, Pageable pageable) {
        if (firstCategoryId == null || secondCategory == null || pageable == null) {
            throw new ApiException(BlueprintErrorCode.NULL_POINT_ERROR,"요청한 객체가 NULL입니다.");
        }

        return blueprintRepository.findAllBySecondCategory(firstCategoryId, secondCategory, InspectionStatus.PASSED, pageable);
    }

    public Page<Blueprint> findAllBlueprintPage(String keyword, Pageable pageable) {
        if (keyword == null || pageable == null) {
            throw new ApiException(BlueprintErrorCode.NULL_POINT_ERROR,"요청한 객체가 NULL입니다.");
        }

        return blueprintRepository.findAllNameAndCreatorContaining(keyword, InspectionStatus.PASSED, pageable);
    }
}