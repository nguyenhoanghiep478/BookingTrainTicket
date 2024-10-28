package com.backend.store.interfacelayer.dto.request;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class PrintTicketRequest extends NotificationRequest{
    private String arrivalStation;
    private String qrCode;
}
