package com.backend.store.interfacelayer.service.QRCode.Impl;

import com.backend.store.interfacelayer.service.QRCode.IQRCodeService;
import com.backend.store.interfacelayer.service.ticket.ITicketService;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class QRCodeServiceImpl implements IQRCodeService {


    @Override
    public byte[] generateQRCode(String text) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat
                .QR_CODE, 200, 200);

        BufferedImage bufferedImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        bufferedImage.createGraphics().fillRect(0, 0, 200, 200);
        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
        graphics.setColor(Color.BLACK);

        for (int x = 0; x < 200; x++) {
            for (int y = 0; y < 200; y++) {
                if (bitMatrix.get(x, y)) {
                    graphics.fillRect(x, y, 1, 1);
                }
            }
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write((RenderedImage) bufferedImage, "png", baos);
        return baos.toByteArray();
    }

    @Override
    public String readQRCode(byte[] imageBytes) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
        BufferedImage bufferedImage = ImageIO.read(bais);

        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        HybridBinarizer binarizer = new HybridBinarizer(source);
        com.google.zxing.BinaryBitmap bitmap = new com.google.zxing.BinaryBitmap(binarizer);
        MultiFormatReader reader = new MultiFormatReader();

        try {
            Result result = reader.decode(bitmap);

            return result.getText();
        } catch (ReaderException e) {
            return "Error reading QR code";
        }
    }

    @Override
    public String generateBase64QRCode(String text) throws WriterException, IOException {
        byte[] qrImage = generateQRCode(text);
        return Base64.getEncoder().encodeToString(qrImage);
    }
}
