package com.lontsi.wellthappback.dto;

import com.lontsi.wellthappback.models.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Data
public class AlimentProposeDto {

    private Integer id;

    private Instant creationDate;

    private Instant lastModifiedDate;

    private String nomAliment;

    private String source;

    private String compositions;

    private String origine;

    private AlimentationDto alimentation;

    private typeRepas typeRepas;

    private  String anecdote;


    public static AlimentProposeDto toDto(AlimentPropose alimentPropose) {
        AlimentProposeDto dto = new AlimentProposeDto();
        dto.setId(alimentPropose.getId());
        dto.setCreationDate(alimentPropose.getCreationDate());
        dto.setLastModifiedDate(alimentPropose.getLastModifiedDate());
        dto.setNomAliment(alimentPropose.getNomAliment());
        dto.setSource(alimentPropose.getSource());
        dto.setCompositions(alimentPropose.getCompositions());
        dto.setOrigine(alimentPropose.getOrigine());
        dto.setAlimentation(AlimentationDto.toDto(alimentPropose.getAlimentation()));
        dto.setTypeRepas(alimentPropose.getTypeRepas());
        dto.setAnecdote(alimentPropose.getAnecdote());
        return dto;
    }

    public static  AlimentPropose toEntity(AlimentProposeDto dto){
        AlimentPropose alimentPropose = new AlimentPropose();

        alimentPropose.setNomAliment(dto.getNomAliment());
        alimentPropose.setSource(dto.getSource());
        alimentPropose.setCompositions(dto.getCompositions());
        alimentPropose.setOrigine(dto.getOrigine());
        alimentPropose.setTypeRepas(dto.getTypeRepas());
        alimentPropose.setAnecdote(dto.getAnecdote());
        if (dto.getAlimentation() !=null){
            alimentPropose.setAlimentation(AlimentationDto.toEntity(dto.getAlimentation()));

        }

        return alimentPropose;
    }
}