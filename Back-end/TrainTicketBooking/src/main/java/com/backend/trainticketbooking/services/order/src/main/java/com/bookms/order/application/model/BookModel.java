package com.bookms.order.application.model;

import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookModel {
    private Integer id;
    private String title;
    private String name;
    private String categoryName;
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;
    private int availableQuantity;
    private String image;
    private Integer chapter;
    private Boolean isInStock;
}
