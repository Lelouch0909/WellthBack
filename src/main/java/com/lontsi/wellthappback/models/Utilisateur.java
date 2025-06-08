package com.lontsi.wellthappback.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Table
@AllArgsConstructor
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Utilisateur extends AbstractEntity {

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "email")
    private String email;


    @Column(name = "age")
    private Integer age;

    @Column(name = "sexe")
    private String sexe;

    @Column(name = "motdepasse")
    private String motDePasse;


    @Column(name = "photo")
    private String photo;

    @Column(name = "poids")
    private Float poids;

    @Column(name = "taille")
    private Float taille;

    @OneToMany(mappedBy = "utilisateur")
    private List<Regime> regime;

    @Column
    private String antecedentsMedicaux;


}
