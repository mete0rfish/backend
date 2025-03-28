package com.onetool.server.api.blueprint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onetool.server.api.blueprint.dto.request.BlueprintRequest;
import com.onetool.server.api.blueprint.dto.response.BlueprintResponse;
import com.onetool.server.api.cart.CartBlueprint;
import com.onetool.server.global.entity.BaseEntity;
import com.onetool.server.api.order.OrderBlueprint;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(indexes = {
        @Index(name = "idx_blueprint_second_category", columnList = "categoryId, secondCategory, inspectionStatus, isDeleted, id DESC")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE Blueprint SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class Blueprint extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "blueprint_name")
    private String blueprintName;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "standard_price")
    private Long standardPrice;

    @Column(name = "blueprint_img")
    private String blueprintImg;

    @Column(name = "blueprint_details")
    private String blueprintDetails;

    private String extension;

    private String program;

    private BigInteger hits;

    @Column(name = "sale_price")
    private Long salePrice;

    @Column(name = "sale_expired_date")
    private LocalDateTime saleExpiredDate;

    @Column(name = "creator_name")
    private String creatorName;

    @Column(name = "download_link")
    private String downloadLink;

    @Column(name = "second_category")
    private String secondCategory;

    @Column(name = "inspection_status")
    @ColumnDefault("'NONE'") // default
    @Enumerated(EnumType.STRING)
    private InspectionStatus inspectionStatus;

    @OneToMany(mappedBy = "blueprint")
    @JsonIgnore
    private List<OrderBlueprint> orderBlueprints = new ArrayList<>();

    @OneToMany(mappedBy = "blueprint")
    @JsonIgnore
    private List<CartBlueprint> cartBlueprints = new ArrayList<>();

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @Column(name = "detail_image")
    private String detailImage;


    @Builder
    public Blueprint(Long id,
                     String blueprintName,
                     Long categoryId,
                     Long standardPrice,
                     String blueprintImg,
                     String blueprintDetails,
                     String extension,
                     String program,
                     BigInteger hits,
                     Long salePrice,
                     LocalDateTime saleExpiredDate,
                     String creatorName,
                     String downloadLink,
                     String secondCategory,
                     InspectionStatus inspectionStatus,
                     List<OrderBlueprint> orderBlueprints,
                     List<CartBlueprint> cartBlueprints,
                     boolean isDeleted,
                     String detailImage) {
        this.id = id;
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
        this.secondCategory = secondCategory;
        this.inspectionStatus = inspectionStatus;
        this.orderBlueprints = orderBlueprints;
        this.cartBlueprints = cartBlueprints;
        this.isDeleted = isDeleted;
        this.detailImage = detailImage;
    }

    public void updateBlueprint(BlueprintResponse blueprintResponse) {
        Optional.ofNullable(blueprintResponse.blueprintName()).ifPresent(name -> this.blueprintName = name);
        Optional.ofNullable(blueprintResponse.categoryId()).ifPresent(id -> this.categoryId = id);
        Optional.ofNullable(blueprintResponse.standardPrice()).ifPresent(price -> this.standardPrice = price);
        Optional.ofNullable(blueprintResponse.blueprintImg()).ifPresent(img -> this.blueprintImg = img);
        Optional.ofNullable(blueprintResponse.blueprintDetails()).ifPresent(details -> this.blueprintDetails = details);
        Optional.ofNullable(blueprintResponse.extension()).ifPresent(ext -> this.extension = ext);
        Optional.ofNullable(blueprintResponse.program()).ifPresent(prog -> this.program = prog);
        Optional.ofNullable(blueprintResponse.hits()).ifPresent(hits -> this.hits = hits);
        Optional.ofNullable(blueprintResponse.salePrice()).ifPresent(salePrice -> this.salePrice = salePrice);
        Optional.ofNullable(blueprintResponse.saleExpiredDate()).ifPresent(expiredDate -> this.saleExpiredDate = expiredDate);
        Optional.ofNullable(blueprintResponse.creatorName()).ifPresent(creator -> this.creatorName = creator);
        Optional.ofNullable(blueprintResponse.downloadLink()).ifPresent(link -> this.downloadLink = link);
        Optional.ofNullable(blueprintResponse.isDeleted()).ifPresent(deleted -> this.isDeleted = deleted);
        Optional.ofNullable(blueprintResponse.detailImage()).ifPresent(detailImg -> this.detailImage = detailImg);
    }

    public void approveBlueprint() {
        this.inspectionStatus = InspectionStatus.PASSED;
    }


    public static Blueprint fromRequest(final BlueprintRequest blueprintRequest) {
        return Blueprint.builder()
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
                .detailImage(blueprintRequest.detailImage())
                .build();
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

}