package com.bookms.order.interfaceLayer.DTO.Request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateQuantityDTO {
    private int id;
    private int quantity;
}
