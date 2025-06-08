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
public class AlimentPropose extends AbstractEntity {

    @Column
    private String nomAliment;

    @Column
    private String source;

    @Column
    private String compositions;

    @Column
    private String origine;

    @ManyToOne
    @JoinColumn(name = "idAlimentation")
    private Alimentation alimentation;

    @Column(name = "repas")
    @Enumerated(EnumType.STRING)
    private typeRepas typeRepas;

    @Column
    private  String anecdote;
}
