package com.onetool.server.api.order;

import com.onetool.server.api.blueprint.Blueprint;
import com.onetool.server.api.payments.domain.Payment;
import com.onetool.server.api.payments.domain.PaymentStatus;
import com.onetool.server.global.entity.BaseEntity;
import com.onetool.server.api.member.domain.Member;
import com.onetool.server.api.payments.TossPayments;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class Orders extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long totalPrice;

    private int totalCount;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'PAY_PENDING'")
    private PaymentStatus status;

    @Setter
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderBlueprint> orderItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_member_id")
    private Member member;

    @OneToOne(mappedBy = "orders")
    private Payment payment;

    public List<String> getOrderItemsDownloadLinks() {
        List<String> orderItemsDownloadLinks = new ArrayList<>();
        if(this.getStatus() == PaymentStatus.PAY_DONE) {
            this.orderItems.forEach(orderBlueprint ->
                    orderItemsDownloadLinks.add(
                            orderBlueprint.getDownloadUrl())

            );
        }
        return orderItemsDownloadLinks;
    }
}