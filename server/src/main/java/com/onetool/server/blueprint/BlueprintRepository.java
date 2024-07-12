package com.onetool.server.blueprint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BlueprintRepository extends JpaRepository<Blueprint, Long> {

    List<Blueprint> findByBlueprintName(String keyword);
    List<Blueprint> findByCreatorName(String keyword);
}
