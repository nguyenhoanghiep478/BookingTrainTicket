package com.backend.store.interfacelayer.dto.objectDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisticTicketByCustomerDTO {
    private String customerName;
    private List<TicketDTO> tickets;

}
