package com.onetool.server.blueprint;

import com.onetool.server.blueprint.dto.SearchResponse;
import org.springframework.data.domain.jaxb.SpringDataJaxb.PageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    private BlueprintService blueprintService;

    public SearchController(BlueprintService blueprintService) {
        this.blueprintService = blueprintService;
    }

    @GetMapping("/search")
    public ResponseEntity searchWithKeyword(
            @RequestParam("s")String keyword,
            PageDto pv,
            @RequestParam("page") int currentPage
    ) {

        SearchResponse response = blueprintService.searchNameAndCreatorWithKeyword(keyword);
        return ResponseEntity.ok().body(response);
    }

}
