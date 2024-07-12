package com.onetool.server.blueprint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BlueprintService {
    @Autowired
    private BlueprintRepository blueprintRepository;

    public Optional<Blueprint> blueprintById(Long id) {
        return blueprintRepository.findById(id);
    }
}