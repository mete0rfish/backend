package com.onetool.server.api.blueprint.business;

import com.onetool.server.api.blueprint.Blueprint;
import com.onetool.server.api.blueprint.dto.request.BlueprintRequest;
import com.onetool.server.api.blueprint.dto.response.BlueprintResponse;
import com.onetool.server.api.blueprint.service.BlueprintService;
import com.onetool.server.global.annotation.Business;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

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
    public void editBlueprint(BlueprintResponse blueprintResponse) {
        Blueprint blueprint = blueprintService.findBlueprintById(blueprintResponse.id());
        blueprintService.updateBlueprint(blueprint, blueprintResponse);
    }

    @Transactional
    public void removeBlueprint(Long blueprintId) {
        Blueprint blueprint = blueprintService.findBlueprintById(blueprintId);
        blueprintService.deleteBlueprint(blueprint);
    }
}
