package com.onetool.server.api.blueprint.business;

import com.onetool.server.api.blueprint.Blueprint;
import com.onetool.server.api.blueprint.dto.request.BlueprintRequest;
import com.onetool.server.api.blueprint.dto.request.BlueprintUpdateRequest;
import com.onetool.server.api.blueprint.service.BlueprintService;
import com.onetool.server.global.annotation.Business;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Business
@RequiredArgsConstructor
public class BlueprintBusiness {

    private final BlueprintService blueprintService;

    @Transactional
    public void createBlueprint(BlueprintRequest blueprintRequest) {
        Blueprint blueprint = Blueprint.fromRequest(blueprintRequest);
        blueprintService.saveBlueprint(blueprint);
    }

    @Transactional
    public void editBlueprint(BlueprintUpdateRequest request) {
        Blueprint blueprint = blueprintService.findBlueprintById(request.id());
        blueprintService.updateBlueprint(blueprint, request);
    }

    @Transactional
    public void removeBlueprint(Long blueprintId) {
        Blueprint blueprint = blueprintService.findBlueprintById(blueprintId);
        blueprintService.deleteBlueprint(blueprint);
    }
}
