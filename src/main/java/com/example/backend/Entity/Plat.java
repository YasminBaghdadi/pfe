package com.example.backend.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
@Data
@Getter
@Setter


public class Plat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPlat;

    private String name;
    private String description;
    private double prix;


    @OneToOne
    @JsonManagedReference
    private Image imagePlat;

    @ManyToMany(mappedBy = "plats")
    private Set<Commande> commandes = new HashSet<>();





}


