package com.onetool.server.category;

import com.onetool.server.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SecondCategory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "first_category_id")
    private FirstCategory firstCategory;
    private String name;

    @Builder
    public SecondCategory(Long id, FirstCategory firstCategory, String name) {
        this.id = id;
        this.firstCategory = firstCategory;
        this.name = name;
    }

}