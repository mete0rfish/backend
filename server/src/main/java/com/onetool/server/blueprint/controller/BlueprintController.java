package com.onetool.server.blueprint.controller;
import com.onetool.server.blueprint.service.BlueprintService;
import com.onetool.server.blueprint.dto.BlueprintRequest;
import com.onetool.server.blueprint.dto.BlueprintResponse;
import com.onetool.server.global.exception.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blueprint")
public class BlueprintController {
    @Autowired
    private BlueprintService blueprintService;

    @PostMapping("/upload")
    public ApiResponse<String> createBlueprint(@RequestBody BlueprintRequest blueprintRequest) {
       blueprintService.createBlueprint(blueprintRequest);
       return ApiResponse.onSuccess("상품이 정상적으로 등록되었습니다.");
    }

    @GetMapping("/{id}")
    public ApiResponse<BlueprintResponse> getBlueprintDetails(@PathVariable Long id) {
        BlueprintResponse blueprintResponseDTO = blueprintService.findBlueprintById(id);
        return ApiResponse.onSuccess(blueprintResponseDTO);
    }

    @PutMapping("/update")
    public ApiResponse<?> updateBlueprint(@RequestBody BlueprintResponse blueprintResponse) {
        blueprintService.updateBlueprint(blueprintResponse);
        return ApiResponse.onSuccess("상품이 정상적으로 수정 되었습니다.");
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<?> deleteBlueprint(@PathVariable Long id){
        blueprintService.deleteBlueprint(id);
        return ApiResponse.onSuccess("상품이 정상적으로 삭제 되었습니다.");
    }

    @GetMapping("/sort")
    public ApiResponse<List<BlueprintResponse>> sortBlueprints(@RequestParam String sortBy) {
        List<BlueprintResponse> sortedBlueprints = blueprintService.sortBlueprints(sortBy);
        return ApiResponse.onSuccess(sortedBlueprints);
    }
}