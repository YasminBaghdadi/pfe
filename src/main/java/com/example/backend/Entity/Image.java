package com.example.backend.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

@Data
@Entity
@Getter
@Setter
public class Image implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idImage;
    private String Name;
    private String type;

    @Lob
    @Column(name = "picByte", columnDefinition  = "LONGBLOB")
    byte[] picByte;


    @OneToOne
    @JoinColumn(name = "idUser", referencedColumnName = "idUser", unique = true, nullable = true)
    @JsonBackReference
    private User user;


    @OneToOne
    @JoinColumn(name="idPlat", referencedColumnName = "idPlat", unique = true)
    @JsonBackReference
    private Plat plat;
}
