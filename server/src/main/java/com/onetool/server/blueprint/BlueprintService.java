package com.onetool.server.blueprint;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BlueprintService {
    @Autowired
    private BlueprintRepository blueprintRepository;

    public Optional<Blueprint> blueprintById(Long blueprintId) {
        return blueprintRepository.findById(blueprintId);
    }
}