package com.onetool.server.order;

import com.onetool.server.blueprint.Blueprint;
import com.onetool.server.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity (name = "order_blueprint")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderBlueprint extends BaseEntity {

    @Id
    @Column(name = "order_blueprint_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //도면관련 매핑은 좀 이따가
    @OneToMany(mappedBy = "orderBlueprint", cascade = CascadeType.ALL)
    private List<Blueprint> blueprints = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "orders_id")
    private Orders order;


}
