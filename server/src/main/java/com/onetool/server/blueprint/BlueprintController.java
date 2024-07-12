package com.onetool.server.blueprint;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class BlueprintController {

    @Autowired
    private BlueprintService blueprintService;

    @GetMapping("blueprint/{blueprintId}") //도면 상세조회
    public ResponseEntity<Blueprint> getBlueprintDetails(@PathVariable Long blueprintId) {
        Optional<Blueprint> blueprint = blueprintService.blueprintById(blueprintId);
        return ResponseEntity.ok(blueprint.get());
    }
}