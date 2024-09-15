package com.bookms.order.core.domain.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItems extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders orders;
    @Column(nullable = false,name = "book_id")
    private Integer bookId;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false,name="total_quantity")
    private Integer totalQuantity;

}
