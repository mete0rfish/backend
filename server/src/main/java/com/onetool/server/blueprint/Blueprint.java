package com.onetool.server.blueprint;

import com.onetool.server.cart.CartBlueprint;
import com.onetool.server.global.entity.BaseEntity;
import com.onetool.server.order.OrderBlueprint;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_blueprint_id")
    private OrderBlueprint orderBlueprint;

    @OneToMany(mappedBy = "blueprint")
    private List<CartBlueprint> cartBlueprints = new ArrayList<>();

    @OneToMany(mappedBy = "blueprint")
    private List<OrderBlueprint> orderBlueprints = new ArrayList<>();

    @Builder
    public Blueprint(String creatorName, LocalDateTime saleExpiredDate, Long salePrice, BigInteger hits, String program, String extension, String blueprintDetails, String blueprintImg, Long standardPrice, Long categoryId, String blueprintName, String downloadLink) {
        this.creatorName = creatorName;
        this.saleExpiredDate = saleExpiredDate;
        this.salePrice = salePrice;
        this.hits = hits;
        this.program = program;
        this.extension = extension;
        this.blueprintDetails = blueprintDetails;
        this.blueprintImg = blueprintImg;
        this.standardPrice = standardPrice;
        this.categoryId = categoryId;
        this.blueprintName = blueprintName;
        this.downloadLink = downloadLink;
    }
}