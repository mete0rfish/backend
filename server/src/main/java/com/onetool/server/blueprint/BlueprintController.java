package com.onetool.server.blueprint;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class BlueprintController {
    @Autowired
    private BlueprintService blueprintService;

    @GetMapping("/blueprint/{id}") // 도면 상세 조회
    public ResponseEntity<Blueprint> getBlueprintDetails(@PathVariable Long id) {
        Optional<Blueprint> blueprint = blueprintService.findBlueprintById(id);
        return blueprint.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}