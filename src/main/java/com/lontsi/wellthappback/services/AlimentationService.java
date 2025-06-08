package com.lontsi.wellthappback.services;

import com.lontsi.wellthappback.dto.AlimentationDto;
import com.lontsi.wellthappback.dto.RegimeDto;
import com.lontsi.wellthappback.models.Alimentation;
import com.lontsi.wellthappback.models.Regime;
import com.lontsi.wellthappback.models.Utilisateur;

import java.util.List;

public interface AlimentationService {


        public AlimentationDto createAlimentation(Alimentation alimentation, Regime regime);


        public  AlimentationDto changeAlimentation(Alimentation Lastalimentation,Alimentation newAlimentation );

        public AlimentationDto findAlimentationById (Integer alimentationId);


        public void deleteAlimentationById(Integer alimentationId);

        public void deleteAllAlimentationByRegimeId(Integer RegimeId);


        public List<AlimentationDto> findAllAlimentationsByRegimeId (Integer RegimeId);
    }
