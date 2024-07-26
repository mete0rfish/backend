package com.onetool.server.blueprint.controller;

import com.onetool.server.blueprint.service.BlueprintService;
import com.onetool.server.blueprint.dto.BlueprintRequest;
import com.onetool.server.blueprint.dto.BlueprintResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blueprint")
public class BlueprintController {
    @Autowired
    private BlueprintService blueprintService;

    @PostMapping("/upload")
    public ResponseEntity<String> createBlueprint(@RequestBody BlueprintRequest blueprintRequest) {
        boolean success = blueprintService.createBlueprint(blueprintRequest);
        if (success) {
            return ResponseEntity.ok("상품이 정상적으로 등록되었습니다.");
        } else {
            return ResponseEntity.status(400).body("상품 등록에 실패하였습니다."); //TODO 예외 추가하기
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlueprintResponse> getBlueprintDetails(@PathVariable Long id) {
        BlueprintResponse blueprintResponseDTO = blueprintService.findBlueprintById(id);
        return ResponseEntity.ok(blueprintResponseDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<Boolean> updateBlueprint(@RequestBody BlueprintResponse blueprintResponse) {
        try {
            boolean success = blueprintService.updateBlueprint(blueprintResponse);
            return ResponseEntity.ok(success);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteBlueprint(@PathVariable Long id){
        return ResponseEntity.ok(blueprintService.deleteBlueprint(id));
    }
}