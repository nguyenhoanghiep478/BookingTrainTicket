package com.booksms.marketing.interfaceLayer.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserRegister {
    private String firstName;
    private String lastName;
    private String recipient;
    private Boolean isVerified;
    private Boolean isFirstVisit;
    private Timestamp lastTimeSendToken;
}
