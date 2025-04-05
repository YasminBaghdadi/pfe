package com.example.backend.Entity;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Table(name = "qrcode")
@Data
public class QRcode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idQrcode;

    private String qrcode; // Texte du QR Code

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] qrImage; // Image du QR Code stockée en base

    @OneToOne
    @JoinColumn(name = "idTable") // Clé étrangère vers Tab
    private Tab tab;
}
