package com.onetool.server.blueprint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BlueprintRepository extends JpaRepository<Blueprint, Long> {

    @Query(value = "SELECT b FROM Blueprint b WHERE b.blueprintName LIKE %:keyword% OR b.creatorName LIKE %:keyword%")
    Page<Blueprint> findAllNameAndCreatorContaining(String keyword, Pageable pageable);

    @Query(value = "SELECT b FROM Blueprint b WHERE b.categoryId = " +
            "(SELECT f.id FROM FirstCategory f WHERE f.name = :category)")
    Page<Blueprint> findAllByFirstCategory(String category, Pageable pageable);

    @Query(value = "SELECT b FROM Blueprint b WHERE b.secondCategory = :second " +
            "AND b.categoryId = (SELECT f.id FROM FirstCategory f WHERE f.name = :first)")
    Page<Blueprint> findAllBySecondCategory(@Param("first") String firstCategory, @Param("second") String secondCategory, Pageable pageable);

    @Query(value = "SELECT count(*) FROM Blueprint b")
    Long countAllBlueprint();
}
