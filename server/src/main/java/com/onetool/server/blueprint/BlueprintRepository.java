package com.onetool.server.blueprint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BlueprintRepository extends JpaRepository<Blueprint, Long> {

    @Query(value = "SELECT b FROM Blueprint b WHERE b.blueprintName LIKE %:keyword% OR b.creatorName LIKE %:keyword%")
    Page<Blueprint> findAllNameAndCreatorContaining(String keyword, Pageable pageable);

    @Query(value = "SELECT b FROM Blueprint b WHERE b.categoryId = " +
            "(SELECT f.id FROM FirstCategory f WHERE f.name = :category)")
    Page<Blueprint> findAllByFirstCategory(String category, Pageable pageable);


    @Query(value = "SELECT b FROM Blueprint b WHERE b.categoryId = " +
            "(SELECT f.id FROM FirstCategory f WHERE f.id = " +
            "(SELECT s.id FROM SecondCategory s WHERE s.name  = :secondCategory))")
    Page<Blueprint> findAllBySecondCategory(String firstCategory, String secondCategory, Pageable pageable);
}
