package com.lontsi.wellthappback.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lontsi.wellthappback.models.Alimentation;
import com.lontsi.wellthappback.models.Regime;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class AlimentationDto {

    private Integer id;

    private Instant creationDate;

    private Instant lastModifiedDate;

    private String nomAlimentation;


    private String nutriments;

    @JsonIgnore
    private List<AlimentProposeDto> alimentsPropose;


    private String jourRegime;

    private RegimeDto regime;

    private  String arg;

    private String source;


    public static AlimentationDto toDto(Alimentation alimentation) {

        AlimentationDto dto = new AlimentationDto();
        dto.setId(alimentation.getId());
        dto.setCreationDate(alimentation.getCreationDate());
        dto.setLastModifiedDate(alimentation.getLastModifiedDate());
        dto.setNomAlimentation(alimentation.getNomAlimentation());
        dto.setNutriments(alimentation.getNutriments());
        dto.setJourRegime(alimentation.getJourRegime());
        dto.setRegime(RegimeDto.toDto(alimentation.getRegime()));
        dto.setArg(alimentation.getArg());
        dto.setSource(alimentation.getSource());
        return dto;
    }

    public static  Alimentation toEntity(AlimentationDto dto){
        Alimentation alimentation = new Alimentation();
        alimentation.setNomAlimentation(dto.getNomAlimentation());
        alimentation.setNutriments(dto.getNutriments());
        alimentation.setJourRegime(dto.getJourRegime());
        alimentation.setArg(dto.getArg());
        alimentation.setSource(dto.getSource());
        if (dto.getRegime() != null){
            alimentation.setRegime(RegimeDto.toEntity(dto.getRegime()));

        }
        return  alimentation;
    }
}
