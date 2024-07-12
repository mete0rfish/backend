package com.onetool.server.blueprint;

import com.onetool.server.blueprint.dto.SearchResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlueprintService {
    
    private BlueprintRepository blueprintRepository;

    public BlueprintService(BlueprintRepository blueprintRepository) {
        this.blueprintRepository = blueprintRepository;
    }
    
    public SearchResponse searchNameAndCreatorWithKeyword(String keyword) {
        List<Blueprint> blueprintList = new ArrayList<>();
        blueprintList.addAll(blueprintRepository.findByBlueprintName(keyword));
        blueprintList.addAll(blueprintRepository.findByCreatorName(keyword));
        return new SearchResponse(blueprintList);
    }
}
