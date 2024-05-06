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
public class Alimentation  extends AbstractEntity{

    @Column
    private String nomAlimentation;

    @Column
    private String nutriments;

    @Column
    private String jourRegime;

    @OneToMany(mappedBy = "alimentation")
    List<AlimentPropose> alimentsPropose;

    @ManyToOne
    @JoinColumn(name="idRegime")
    private Regime regime;

    @Column(columnDefinition = "TEXT", length = 2000)
    private String arg;

    @Column
    private String source;
}
