package com.onetool.server.blueprint.dto;

import com.onetool.server.blueprint.Blueprint;
import lombok.Builder;

import java.util.List;

public record SearchResponse(
        List<Blueprint> blueprintList
) {

    @Builder
    public SearchResponse(List<Blueprint> blueprintList) {
        this.blueprintList = blueprintList;
    }

}
