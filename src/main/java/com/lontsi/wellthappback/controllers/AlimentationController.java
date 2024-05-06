package com.lontsi.wellthappback.controllers;

import com.lontsi.wellthappback.controllers.api.AlimentationApi;
import com.lontsi.wellthappback.dto.AlimentProposeDto;
import com.lontsi.wellthappback.dto.AlimentationDto;
import com.lontsi.wellthappback.dto.RegimeDto;
import com.lontsi.wellthappback.dto.UtilisateurDto;
import com.lontsi.wellthappback.exceptions.UtilisateurException;
import com.lontsi.wellthappback.models.Alimentation;
import com.lontsi.wellthappback.models.Regime;
import com.lontsi.wellthappback.models.Utilisateur;
import com.lontsi.wellthappback.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.lontsi.wellthappback.config.Utils.ALIMENTATION_ENDPOINT;

@RestController(ALIMENTATION_ENDPOINT)
public class AlimentationController implements AlimentationApi {

    @Autowired
    private AiService aiService;

    @Autowired
    private AlimentationService alimentationService;

    @Autowired
    private AlimentProposeService alimentProposeService;

    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private RegimeService regimeService;

    @Override
    public AlimentationDto createAlimentation(RegimeDto regime, String jwt) throws UtilisateurException {
        UtilisateurDto reqUser = utilisateurService.findUserByJwt(jwt);
        AlimentationDto alimentationDto = aiService.createAlimentation(regime, reqUser);
        Alimentation alimentation = AlimentationDto.toEntity(alimentationDto);
        List<AlimentProposeDto> alimentProposeDtos = new ArrayList<>();
        for (AlimentProposeDto alimentProposeDto : alimentationDto.getAlimentsPropose()) {
            alimentProposeDtos.add(alimentProposeService.createAlimentPropose(AlimentProposeDto.toEntity(alimentProposeDto), alimentation));
        }
        AlimentationDto toReturn = alimentationService.createAlimentation(alimentation, RegimeDto.toEntity(regime));
         toReturn.setAlimentsPropose(alimentProposeDtos);
        return toReturn;
    }

    @Override
    public AlimentationDto changerAlimentation(AlimentationDto alimentationDto, Integer regimeid,  String jwt) throws UtilisateurException {
        
        AlimentationDto toReturn = createAlimentation(regimeService.findRegimeById(regimeid),jwt);
        alimentationService.changeAlimentation(AlimentationDto.toEntity(alimentationDto),AlimentationDto.toEntity(toReturn));
        return toReturn;
    }

    @Override
    public AlimentationDto findAlimentationById(Integer alimentationId) {
        return alimentationService.findAlimentationById(alimentationId);
    }

    @Override
    public List<AlimentationDto> findAllAlimentationsByRegimeId(Integer id) {
        return alimentationService.findAllAlimentationsByRegimeId(id);
    }
}
