package com.onetool.server.api.blueprint.business;

import com.onetool.server.api.blueprint.service.BlueprintInspectionService;
import com.onetool.server.global.annotation.Business;
import lombok.RequiredArgsConstructor;

@Business
@RequiredArgsConstructor
public class BlueprintInspectionBusiness {

    private final BlueprintInspectionService blueprintInspectionService;


}
