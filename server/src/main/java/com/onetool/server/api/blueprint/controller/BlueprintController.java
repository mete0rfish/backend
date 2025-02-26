package com.onetool.server.api.blueprint.controller;

import com.onetool.server.api.blueprint.business.BlueprintBusiness;
import com.onetool.server.api.blueprint.dto.request.BlueprintRequest;
import com.onetool.server.api.blueprint.dto.response.BlueprintResponse;
import com.onetool.server.api.blueprint.service.BlueprintService;
import com.onetool.server.global.auth.login.PrincipalDetails;
import com.onetool.server.global.exception.ApiResponse;
import com.onetool.server.global.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blueprint")
public class BlueprintController {

    private final BlueprintBusiness blueprintBusiness;

    @PostMapping("/upload")
    public ApiResponse<String> postBlueprint(
            @RequestBody BlueprintRequest blueprintRequest,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        if (principalDetails == null) {
            throw new MemberNotFoundException(null);
        }
        blueprintBusiness.createBlueprint(blueprintRequest);
        return ApiResponse.onSuccess("상품이 정상적으로 등록되었습니다.");
    }

    @PutMapping("/update")
    public ApiResponse<String> putBlueprint(
            @RequestBody BlueprintResponse blueprintResponse,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        if (principalDetails == null) {
            throw new MemberNotFoundException(null);
        }
        blueprintBusiness.editBlueprint(blueprintResponse);
        return ApiResponse.onSuccess("상품이 정상적으로 수정 되었습니다.");
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> deleteBlueprint(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        if (principalDetails == null) {
            throw new MemberNotFoundException(null);
        }

        blueprintBusiness.removeBlueprint(id);
        return ApiResponse.onSuccess("상품이 정상적으로 삭제 되었습니다.");
    }
}
