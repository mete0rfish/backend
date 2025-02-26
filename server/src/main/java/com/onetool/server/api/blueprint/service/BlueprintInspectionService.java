package com.onetool.server.api.blueprint.service;

import com.onetool.server.api.blueprint.Blueprint;
import com.onetool.server.api.blueprint.dto.response.BlueprintResponse;
import com.onetool.server.api.blueprint.repository.BlueprintRepository;
import com.onetool.server.api.blueprint.InspectionStatus;
import com.onetool.server.global.exception.BlueprintNotFoundException;
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
                .orElseThrow(() -> new BlueprintNotFoundException(blueprintId + "는 DB에 존재하지 않습니다. 함수명 : findBluePrintById"));
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
            throw new NullPointerException("blueprint 객체는 NULL입니다. 함수명 : validateBlueprintIsNull");
        }
    }
}