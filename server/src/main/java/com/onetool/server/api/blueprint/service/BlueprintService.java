package com.onetool.server.api.blueprint.service;

import com.onetool.server.api.blueprint.Blueprint;
import com.onetool.server.api.blueprint.dto.request.BlueprintRequest;
import com.onetool.server.api.blueprint.dto.response.BlueprintResponse;
import com.onetool.server.api.blueprint.repository.BlueprintRepository;
import com.onetool.server.global.exception.BlueprintNotFoundException;
import com.onetool.server.global.exception.BlueprintNullPointException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Set;


@Service
@Transactional
@RequiredArgsConstructor
public class BlueprintService {

    private final BlueprintRepository blueprintRepository;

    public List<Blueprint> findAllBlueprintByIds(Set<Long> blueprintIds) {
        List<Blueprint> blueprintList = blueprintRepository.findAllById(blueprintIds);

        if (blueprintList.size() != blueprintIds.size()) {
            throw new BlueprintNotFoundException("요청 하신 Blueprint중 일부가 존재하지 않습니다.");
        }
        return blueprintList;
    }

    public Blueprint findBlueprintById(Long blueprintId) {
        return blueprintRepository.findById(blueprintId)
                .orElseThrow(() -> new BlueprintNotFoundException(blueprintId + " 해당 id는 DB에 존재하지 않습니다."));
    }

    public void saveBlueprint(Blueprint blueprint) {
        validateBlueprintIsNull(blueprint);
        blueprintRepository.save(blueprint);
    }

    public void updateBlueprint(Blueprint blueprint, BlueprintResponse blueprintResponse) {
        validateBlueprintIsNull(blueprint);
        blueprint.updateBlueprint(blueprintResponse);
    }

    public void deleteBlueprint(Blueprint blueprint) {
        validateBlueprintIsNull(blueprint);
        blueprintRepository.delete(blueprint);
    }

    private void validateBlueprintIsNull(Blueprint blueprint) {
        if (blueprint == null) {
            throw new BlueprintNullPointException("blueprint는 NULL입니다. 함수명 : validateBulePrintIsNull");
        }
    }
}
