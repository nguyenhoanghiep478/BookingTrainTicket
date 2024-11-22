package com.booksms.marketing.interfaceLayer.dto.request;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrintTicketRequest extends NotificationsDepartureTime{
    private String arrivalStation;
    private String qrCode;
}
