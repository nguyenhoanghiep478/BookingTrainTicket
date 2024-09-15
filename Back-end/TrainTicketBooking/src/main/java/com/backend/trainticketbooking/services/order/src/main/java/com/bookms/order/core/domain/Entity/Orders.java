package com.bookms.order.core.domain.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique=true,nullable=false)
    private Long orderNumber;
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private OrderType orderType;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
    @Column(unique=true,nullable=false)
    private int customerId;
    @Column(nullable=false)
    private BigDecimal totalPrice;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true,mappedBy = "orders")
    private List<OrderItems> orderItems;
    @Column(nullable=false)
    private String paymentMethod;
    @Column(nullable=false)
    private Integer paymentId;

    @Column(nullable=false)
    private Integer shipmentId;

    @PrePersist
    public void prePersist(){
        if(this.status == null){
            this.status = Status.PENDING;
        }
        if(this.orderType == null){
            this.orderType = OrderType.SELL;
        }
    }
}
