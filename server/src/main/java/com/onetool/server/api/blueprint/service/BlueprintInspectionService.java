package com.onetool.server.api.blueprint.service;

import com.onetool.server.api.blueprint.Blueprint;
import com.onetool.server.api.blueprint.dto.response.BlueprintResponse;
import com.onetool.server.api.blueprint.repository.BlueprintRepository;
import com.onetool.server.api.blueprint.InspectionStatus;
import com.onetool.server.global.exception.BlueprintNotFoundException;
import com.onetool.server.global.new_exception.exception.ApiException;
import com.onetool.server.global.new_exception.exception.error.BlueprintErrorCode;
import com.onetool.server.global.new_exception.exception.error.CartErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class BlueprintInspectionService {

    private final BlueprintRepository blueprintRepository;

    @Transactional
    public Page<Blueprint> findAllNotPassedBlueprintsWithPage(Pageable pageable) {
        Page<Blueprint> blueprintPage = blueprintRepository.findByInspectionStatus(InspectionStatus.NONE, pageable);
        return blueprintPage;
    }

    public Blueprint findBluePrintById(Long blueprintId) {
        return blueprintRepository.findById(blueprintId)
                .orElseThrow(() -> new ApiException(BlueprintErrorCode.NOT_FOUND_ERROR,"blueprintId : "+ blueprintId));
    }

    public void updateBlueprintInspectionStatus(Blueprint blueprint) {
        validateBlueprintIsNull(blueprint);
        blueprint.approveBlueprint();
    }

    public void deleteBlueprintById(Long id) {
        blueprintRepository.deleteById(id);
    }

    private void validateBlueprintIsNull(Blueprint blueprint) {
        if (blueprint == null) {
            throw new ApiException(BlueprintErrorCode.NULL_POINT_ERROR,"blueprint가 NULL입니다.");
        }
    }
}