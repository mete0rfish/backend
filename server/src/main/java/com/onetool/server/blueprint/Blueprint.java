package com.onetool.server.blueprint;

import com.onetool.server.category.FirstCategory;
import com.onetool.server.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;

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
    @Column(name = "standard_price")
    private Long standardPrice;
    @Column(name = "blueprint_img")
    private String blueprintImg;
    @Column(name = "blueprint_details")
    private String blueprintDetails;
    @Column(name = "extension")
    private String extension;
    @Column(name = "program")
    private String program;
    @Column(name = "hits")
    private BigInteger hits;
    @Column(name = "sale_price")
    private Long salePrice;
    @Column(name = "sale_expired_date")
    private LocalDateTime saleExpiredDate;
    @Column(name = "creator_name")
    private String creatorName;
    @Column(name = "download_link")
    private String downloadLink;

    @Builder
    public Blueprint(String blueprintName, Long categoryId, Long standardPrice, String blueprintImg, String blueprintDetails, String extension, String program, BigInteger hits, Long salePrice, LocalDateTime saleExpiredDate, String creatorName, String downloadLink) {
        this.blueprintName = blueprintName;
        this.categoryId = categoryId;
        this.standardPrice = standardPrice;
        this.blueprintImg = blueprintImg;
        this.blueprintDetails = blueprintDetails;
        this.extension = extension;
        this.program = program;
        this.hits = hits;
        this.salePrice = salePrice;
        this.saleExpiredDate = saleExpiredDate;
        this.creatorName = creatorName;
        this.downloadLink = downloadLink;
    }
}