package com.example.backend.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Entity
@Data
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCmnd;

    @Setter
    private double total;

    @ManyToOne
    @JoinColumn(name = "idUser", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "commande_plat",
            joinColumns = @JoinColumn(name = "idCmnd"),
            inverseJoinColumns = @JoinColumn(name = "idPlat")
    )
    @JsonManagedReference
    private List<Plat> plats;



    public void calculerTotal() {
        this.total = plats.stream()
                .mapToDouble(Plat::getPrix)
                .sum();
    }

    @ManyToOne
    @JoinColumn(name = "idTable", nullable = false)
    private Tab tab;
}
