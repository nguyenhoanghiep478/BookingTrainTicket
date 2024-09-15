package com.bookms.order.interfaceLayer.DTO;

import com.bookms.order.config.CustomBigDecimalDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Digits;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Integer id;
    private String title;
    private String name;
    private Integer categoryId;
    @JsonDeserialize(using = CustomBigDecimalDeserializer.class)
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;
    private Integer availableQuantity;
    private String image;
    private Integer chapter;
    private Boolean inStock;
}
