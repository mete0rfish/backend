package com.onetool.server.blueprint;

import com.onetool.server.blueprint.dto.SearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlueprintService {

    private BlueprintRepository blueprintRepository;

    public BlueprintService(BlueprintRepository blueprintRepository) {
        this.blueprintRepository = blueprintRepository;
    }

    public Optional<Blueprint> findBlueprintById(Long id) {
        return blueprintRepository.findById(id);
    }

    public Page<SearchResponse> searchNameAndCreatorWithKeyword(String keyword, Pageable pageable) {
        Page<Blueprint> result = blueprintRepository.findAllNameAndCreatorContaining(keyword, pageable);
        List<SearchResponse> list = result.getContent().stream()
                .map(SearchResponse::from)
                .collect(Collectors.toList());
        return new PageImpl<>(list, pageable, result.getTotalElements());
    }
}