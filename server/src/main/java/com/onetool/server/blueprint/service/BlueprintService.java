package com.onetool.server.blueprint.service;

import com.onetool.server.blueprint.Blueprint;
import com.onetool.server.blueprint.repository.BlueprintRepository;
import com.onetool.server.blueprint.dto.BlueprintRequest;
import com.onetool.server.blueprint.dto.BlueprintResponse;
import com.onetool.server.blueprint.dto.SearchResponse;
import com.onetool.server.category.FirstCategoryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlueprintService {

    private BlueprintRepository blueprintRepository;

    public BlueprintService(BlueprintRepository blueprintRepository) {
        this.blueprintRepository = blueprintRepository;
    }

    public List<BlueprintResponse> findAllBlueprints(){
        List<Blueprint> blueprints = blueprintRepository.findAll();

        return blueprints.stream()
                .map(BlueprintResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public BlueprintResponse findBlueprintById(Long id) {
        Blueprint blueprint = blueprintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blueprint not found with id: " + id));

        return BlueprintResponse.fromEntity(blueprint);
    }

    public boolean createBlueprint(BlueprintRequest blueprintRequest) {
        try {
            Blueprint blueprint = Blueprint.builder()
                    .id(blueprintRequest.id())
                    .blueprintName(blueprintRequest.blueprintName())
                    .categoryId(blueprintRequest.categoryId())
                    .standardPrice(blueprintRequest.standardPrice())
                    .blueprintImg(blueprintRequest.blueprintImg())
                    .blueprintDetails(blueprintRequest.blueprintDetails())
                    .extension(blueprintRequest.extension())
                    .program(blueprintRequest.program())
                    .hits(blueprintRequest.hits())
                    .salePrice(blueprintRequest.salePrice())
                    .saleExpiredDate(blueprintRequest.saleExpiredDate())
                    .creatorName(blueprintRequest.creatorName())
                    .downloadLink(blueprintRequest.downloadLink())
                    .build();

            saveBlueprint(blueprint);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Blueprint saveBlueprint(Blueprint blueprint) {
        return blueprintRepository.save(blueprint);
    }

    public boolean updateBlueprint(BlueprintResponse blueprintResponse) {
        Blueprint existingBlueprint = blueprintRepository.findById(blueprintResponse.id())
                .orElseThrow(() -> new RuntimeException("Blueprint not found with id: " + blueprintResponse.id()));

        Blueprint updatedBlueprint = Blueprint.builder()
                .id(existingBlueprint.getId())
                .blueprintName(blueprintResponse.blueprintName())
                .categoryId(blueprintResponse.categoryId())
                .standardPrice(blueprintResponse.standardPrice())
                .blueprintImg(blueprintResponse.blueprintImg())
                .blueprintDetails(blueprintResponse.blueprintDetails())
                .extension(blueprintResponse.extension())
                .program(blueprintResponse.program())
                .hits(blueprintResponse.hits())
                .salePrice(blueprintResponse.salePrice())
                .saleExpiredDate(blueprintResponse.saleExpiredDate())
                .creatorName(blueprintResponse.creatorName())
                .downloadLink(blueprintResponse.downloadLink())
                .build();

        blueprintRepository.save(updatedBlueprint);
        return true;
    }

    public boolean deleteBlueprint(Long id){
        blueprintRepository.deleteById(id);
        return true;
    };

    public List<BlueprintResponse> sortBlueprints(String sortBy) {
        List<Blueprint> blueprints = blueprintRepository.findAll();

        Comparator<Blueprint> comparator = getComparator(sortBy);

        List<Blueprint> sortedBlueprints = blueprints.stream()
                .sorted(comparator)
                .collect(Collectors.toList());

        return sortedBlueprints.stream()
                .map(BlueprintResponse::fromEntity)
                .collect(Collectors.toList());
    }

    private Comparator<Blueprint> getComparator(String sortBy) {
        switch (sortBy) {
            case "price":
                return Comparator.comparing(blueprint -> {
                    if (blueprint.getSalePrice() != null && blueprint.getSaleExpiredDate() != null
                            && blueprint.getSaleExpiredDate().isAfter(LocalDateTime.now())) {
                        return blueprint.getSalePrice();
                    } else {
                        return blueprint.getStandardPrice();
                    }
                }, Comparator.nullsLast(Comparator.naturalOrder()));

            case "createdAt":
                return Comparator.comparing(Blueprint::getCreatedAt, Comparator.nullsLast(Comparator.naturalOrder()));

            case "extension":
                return Comparator.comparing(Blueprint::getExtension, Comparator.nullsLast(Comparator.naturalOrder()));

            /*case "saleCount":
                return Comparator.comparing(Blueprint::getSaleCount, Comparator.nullsLast(Comparator.naturalOrder()));

             */
            default:
                throw new IllegalArgumentException("Invalid sort type: " + sortBy);
        }
    }

    public Page<SearchResponse> searchNameAndCreatorWithKeyword(String keyword, Pageable pageable) {
        Page<Blueprint> result = blueprintRepository.findAllNameAndCreatorContaining(keyword, pageable);
        List<SearchResponse> list = result.getContent().stream()
                .map(SearchResponse::from)
                .collect(Collectors.toList());
        return new PageImpl<>(list, pageable, result.getTotalElements());
    }

    public Page<SearchResponse> findAllByFirstCategory(FirstCategoryType category, Pageable pageable) {
        Page<Blueprint> result = blueprintRepository.findAllByFirstCategory(category.getType(), pageable);
        List<SearchResponse> list = result.getContent().stream()
                .map(SearchResponse::from)
                .collect(Collectors.toList());
        return new PageImpl<>(list, pageable, result.getTotalElements());
    }

    public Page<SearchResponse> findAllBySecondCategory(FirstCategoryType firstCategory, String secondCategory, Pageable pageable) {
        Page<Blueprint> result = blueprintRepository.findAllBySecondCategory(
                firstCategory.getType(), secondCategory, pageable);
        List<SearchResponse> list = result.getContent().stream()
                .map(SearchResponse::from)
                .collect(Collectors.toList());
        return new PageImpl<>(list, pageable, result.getTotalElements());
    }

    public Page<SearchResponse> findAll(Pageable pageable) {
        Page<Blueprint> result = blueprintRepository.findAll(pageable);
        List<SearchResponse> list = result.getContent().stream()
                .map(SearchResponse::from)
                .collect(Collectors.toList());
        return new PageImpl<>(list, pageable, result.getTotalElements());
    }
}