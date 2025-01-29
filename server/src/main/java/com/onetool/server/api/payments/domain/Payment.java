package com.onetool.server.api.payments.domain;

import com.onetool.server.api.order.Orders;
import com.onetool.server.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountName;
    private String accountNumber;
    private String bankName;
    private Long totalPrice;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "payment_blueprint_id")
//    private Set<PaymentBlueprint> numbers;

    @OneToOne
    @JoinColumn(name = "orders_id")
    private Orders orders;
}
