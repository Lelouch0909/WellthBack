package com.lontsi.wellthappback.services;

import com.lontsi.wellthappback.dto.AlimentProposeDto;
import com.lontsi.wellthappback.dto.AlimentationDto;
import com.lontsi.wellthappback.dto.RegimeDto;
import com.lontsi.wellthappback.dto.UtilisateurDto;
import com.lontsi.wellthappback.exceptions.UtilisateurException;

public interface AiService {

    public RegimeDto createRegime (UtilisateurDto reqUser, RegimeDto regimeDto);

    public AlimentationDto createAlimentation (RegimeDto reqRegime,UtilisateurDto dto);


    AlimentProposeDto createAlimentPropose(AlimentProposeDto alimentProposeDto, AlimentationDto alimentationDto, RegimeDto regimeDto, UtilisateurDto reqUser);

    public String askToAi (String message);


}
