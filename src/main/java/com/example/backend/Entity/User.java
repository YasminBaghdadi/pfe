package com.example.backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Data
@Table(name = "User")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUser;

    @Column(nullable = false)
    private String username;

    private String firstname;
    private String lastname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String confirmPassword;

    @ManyToOne
    @JoinColumn(name = "idRole", nullable = false)
    @JsonIgnore
    private Role role;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, optional = true)
    @JsonManagedReference
    private Image image;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Commande> commandes = new HashSet<>();


}

