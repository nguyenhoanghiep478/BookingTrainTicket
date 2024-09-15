package com.booksms.marketing.interfaceLayer.service;

import com.booksms.marketing.interfaceLayer.dto.VerifyUserDTO;
import com.booksms.marketing.interfaceLayer.dto.request.EmailRequest;

public interface IEmailService {
    void sendSimpleMail(EmailRequest build);

    String verifyToken(VerifyUserDTO verifyUserDTO);
}
