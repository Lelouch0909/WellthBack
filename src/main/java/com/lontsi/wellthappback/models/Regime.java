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
public class Regime extends AbstractEntity {

    @Column
    private float objectif;

    @Column
    private String nomRegime;

    @Column
    private Integer dureeEnJours;

    @Column
    private float calorieQuotidien;

    @Column
    private String nomCreateurRegime;

    @ManyToOne
    @JoinColumn(name = "idutilisateur")
    private Utilisateur utilisateur;


    @OneToMany(mappedBy = "regime")
    private List<Alimentation> alimentations;


    @Column
    private String alimentsAeviter;

    @Column
    private String alimentationHabituelle;

    @Column
    @Enumerated(EnumType.STRING)
    private RegimeObjectif regimeObjectif;


}
