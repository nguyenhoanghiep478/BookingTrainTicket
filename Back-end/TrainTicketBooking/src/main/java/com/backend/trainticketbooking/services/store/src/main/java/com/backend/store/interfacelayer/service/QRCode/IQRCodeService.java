package com.backend.store.interfacelayer.service.QRCode;

import com.google.zxing.WriterException;

import java.io.IOException;

public interface IQRCodeService {
    byte[] generateQRCode(String text) throws WriterException, IOException;

    String readQRCode(byte[] imageBytes) throws IOException;

    String generateBase64QRCode(String text) throws WriterException, IOException;
}
