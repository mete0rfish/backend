package com.onetool.server.api.blueprint.service;

import com.onetool.server.api.blueprint.Blueprint;
import com.onetool.server.api.blueprint.dto.request.BlueprintRequest;
import com.onetool.server.api.blueprint.dto.response.BlueprintResponse;
import com.onetool.server.api.blueprint.repository.BlueprintRepository;
import com.onetool.server.global.exception.BlueprintNotFoundException;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Set;


@Service
@Transactional
public class BlueprintService {
    private final BlueprintRepository blueprintRepository;

    public BlueprintService(BlueprintRepository blueprintRepository) {
        this.blueprintRepository = blueprintRepository;
    }

   public List<Blueprint> findAllBlueprintByIds(Set<Long> blueprintIds){
       List<Blueprint> blueprintList = blueprintRepository.findAllById(blueprintIds);

       if(blueprintList.size() != blueprintIds.size()){
           throw new BlueprintNotFoundException("요청 하신 Blueprint중 일부가 존재하지 않습니다.");
       }
       return blueprintList;
   }

    public void createBlueprint(final BlueprintRequest blueprintRequest) {
        Blueprint blueprint = convertToBlueprint(blueprintRequest);
        blueprintRepository.save(blueprint);
    }

    public void updateBlueprint(BlueprintResponse blueprintResponse) {
        Blueprint existingBlueprint = blueprintRepository.findById(blueprintResponse.id())
                .orElseThrow(() -> new BlueprintNotFoundException(blueprintResponse.id().toString()));

        Blueprint updatedBlueprint = updateExistingBlueprint(existingBlueprint, blueprintResponse);
        blueprintRepository.save(updatedBlueprint);
    }

    public void deleteBlueprint(Long id) {
        blueprintRepository.findById(id)
                .orElseThrow(() -> new BlueprintNotFoundException(id.toString()));

        blueprintRepository.deleteById(id);
    }

    private Blueprint convertToBlueprint(final BlueprintRequest blueprintRequest) {
        return Blueprint.fromRequest(blueprintRequest);
    }

    private Blueprint updateExistingBlueprint(Blueprint existingBlueprint, BlueprintResponse blueprintResponse) {
        return Blueprint.builder()
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
    }
}