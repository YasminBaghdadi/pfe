package com.example.backend.Controller;


import com.example.backend.Entity.QRcode;
import com.example.backend.Entity.Tab;
import com.example.backend.Repository.QRcodeRepository;
import com.example.backend.Repository.TabRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/qr")
public class QRcodeController {
    @Autowired
    private TabRepository tableRepository;

    @Autowired
    private QRcodeRepository qrCodeRepository;

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    // ✅ Générer et enregistrer un QR Code pour une table donnée
    @PostMapping("/generate/{idTab}")
    public ResponseEntity<String> generateAndSaveQrCode(@PathVariable Long idTab, @RequestBody Map<String, String> body) {
        Optional<Tab> tableOpt = tableRepository.findById(idTab);
        if (tableOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Table non trouvée");
        }

        Tab table = tableOpt.get();

        String qrText = body.get("qrText");

        try {
            byte[] qrCodeImage = generateQrCodeImage(qrText, WIDTH, HEIGHT);

            QRcode qrCode = new QRcode();
            qrCode.setQrcode(qrText);
            qrCode.setQrImage(qrCodeImage);
            qrCode.setTab(table);

            qrCodeRepository.save(qrCode);
            return ResponseEntity.ok("QR Code généré et enregistré avec succès !");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la génération du QR Code");
        }
    }


    // ✅ Récupérer l’image du QR Code d’une table
    @GetMapping("/image/{idTab}")
    public ResponseEntity<byte[]> getQrCodeImage(@PathVariable Long idTab) {
        Optional<Tab> tableOpt = tableRepository.findById(idTab);
        if (tableOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Tab table = tableOpt.get();
        Optional<QRcode> qrCodeOpt = qrCodeRepository.findByTab(table);
        if (qrCodeOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        byte[] qrCodeImage = qrCodeOpt.get().getQrImage();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qrCodeImage);
    }

    private byte[] generateQrCodeImage(String qrText, int width, int height) throws IOException {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrText, BarcodeFormat.QR_CODE, width, height);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream);

            return byteArrayOutputStream.toByteArray();
        } catch (WriterException e) {
            throw new IOException("Erreur lors de la génération du QR Code", e);
        }
    }
}

