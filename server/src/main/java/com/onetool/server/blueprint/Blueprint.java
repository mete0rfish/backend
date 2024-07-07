package com.onetool.server.blueprint;

import com.onetool.server.category.FirstCategory;
import com.onetool.server.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Blueprint extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="blueprint_name")
    private String blueprintName;
    @Column(name="category_id")
    private Long categoryId;

    @Column(name = "blueprint_price")
    private Long blueprintPrice;
    @Column(name = "blueprint_img")
    private String blueprintImg;
    @Column(name = "blueprint_details")
    private String blueprintDetails;
    @Column(name = "extension")
    private String extension;
    @Column(name = "program")
    private String program;
    @Column(name = "his")
    private BigInteger his;

    @Builder
    public Blueprint(Long id, String blueprintName, Long categoryId, Long blueprintPrice, String blueprintImg, String blueprintDetails, String extension, String program, BigInteger his) {
        this.id = id;
        this.blueprintName = blueprintName;
        this.categoryId = categoryId;
        this.blueprintPrice = blueprintPrice;
        this.blueprintImg = blueprintImg;
        this.blueprintDetails = blueprintDetails;
        this.extension = extension;
        this.program = program;
        this.his = his;
    }
}