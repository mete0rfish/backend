package com.onetool.server.blueprint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BlueprintRepository extends JpaRepository<Blueprint, Long> {

    @Query(value = "SELECT b FROM Blueprint b WHERE b.blueprintName LIKE %:keyword% AND b.creatorName LIKE %:keyword%")
    Page<Blueprint> findAllNameAndCreatorContaining(String keyword, Pageable pageable);

}
