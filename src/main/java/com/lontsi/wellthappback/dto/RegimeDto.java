package com.lontsi.wellthappback.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lontsi.wellthappback.models.Alimentation;
import com.lontsi.wellthappback.models.Regime;
import com.lontsi.wellthappback.models.RegimeObjectif;
import com.lontsi.wellthappback.models.Utilisateur;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class RegimeDto {

    private Integer id;

    private Instant creationDate;


    private Instant lastModifiedDate;

    private float objectif;


    private String nomRegime;


    private Integer dureeEnJours;


    private float calorieQuotidien;


    private String nomCreateurRegime;

    private UtilisateurDto utilisateur;
    @JsonIgnore
    private List<AlimentationDto> alimentations;


    private String alimentsAeviter;

    private String alimentationHabituelle;

    private RegimeObjectif regimeObjectif;


    public static RegimeDto toDto(Regime regime) {


        RegimeDto dto = new RegimeDto();
        dto.setId(regime.getId());
        dto.setCreationDate(regime.getCreationDate());
        dto.setLastModifiedDate(regime.getLastModifiedDate());
        dto.setObjectif(regime.getObjectif());
        dto.setNomRegime(regime.getNomRegime());
        dto.setDureeEnJours(regime.getDureeEnJours());
        dto.setCalorieQuotidien(regime.getCalorieQuotidien());
        dto.setNomCreateurRegime(regime.getNomCreateurRegime());
        dto.setAlimentsAeviter(regime.getAlimentsAeviter());
        dto.setAlimentationHabituelle(regime.getAlimentationHabituelle());
        dto.setRegimeObjectif(regime.getRegimeObjectif());

        dto.setUtilisateur(UtilisateurDto.toDto(regime.getUtilisateur()));


        return dto;
    }

    public static Regime toEntity(RegimeDto dto) {
        Regime regime = new Regime();
        regime.setObjectif(dto.getObjectif());
        regime.setNomRegime(dto.getNomRegime());
        regime.setDureeEnJours(dto.getDureeEnJours());
        regime.setCalorieQuotidien(dto.getCalorieQuotidien());
        regime.setNomCreateurRegime(dto.getNomCreateurRegime());
        regime.setAlimentsAeviter(dto.getAlimentsAeviter());
        regime.setAlimentationHabituelle(dto.getAlimentationHabituelle());
        regime.setRegimeObjectif(dto.getRegimeObjectif());
        if (dto.getUtilisateur() != null) {
            regime.setUtilisateur(UtilisateurDto.toEntity(dto.getUtilisateur()));

        }

        return regime;
    }

}
