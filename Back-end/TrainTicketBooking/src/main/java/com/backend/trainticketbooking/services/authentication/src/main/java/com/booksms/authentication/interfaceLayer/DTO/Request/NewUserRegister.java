package com.booksms.authentication.interfaceLayer.DTO.Request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewUserRegister {
    private String firstName;
    private String lastName;
    private String recipient;
    private Boolean isVerified;
    private Boolean isFirstVisit;
    private Boolean isBlocked;
}