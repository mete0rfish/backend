package com.onetool.server.api.blueprint.business;

import com.onetool.server.api.blueprint.Blueprint;
import com.onetool.server.api.blueprint.dto.response.BlueprintResponse;
import com.onetool.server.api.blueprint.service.BlueprintInspectionService;
import com.onetool.server.global.annotation.Business;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Business
@RequiredArgsConstructor
public class BlueprintInspectionBusiness {

    private final BlueprintInspectionService blueprintInspectionService;

    @Transactional
    public List<BlueprintResponse> getNotPassedBlueprintList(Pageable pageable) {

        Page<Blueprint> blueprintPage = blueprintInspectionService.findAllNotPassedBlueprintsWithPage(pageable);

        List<BlueprintResponse> blueprintResponses = BlueprintResponse.fromBlueprintPageToResponseList(blueprintPage);
        return blueprintResponses;
    }

    @Transactional
    public void editBlueprintWithApprove(Long blueprintId) {
        Blueprint blueprint = blueprintInspectionService.findBluePrintById(blueprintId);
        blueprintInspectionService.updateBlueprintInspectionStatus(blueprint);
    }

}
