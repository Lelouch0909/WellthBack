package com.lontsi.wellthappback.controllers;

import com.lontsi.wellthappback.controllers.api.RegimeApi;
import com.lontsi.wellthappback.dto.AlimentProposeDto;
import com.lontsi.wellthappback.dto.AlimentationDto;
import com.lontsi.wellthappback.dto.RegimeDto;
import com.lontsi.wellthappback.dto.UtilisateurDto;
import com.lontsi.wellthappback.exceptions.UtilisateurException;
import com.lontsi.wellthappback.models.Alimentation;
import com.lontsi.wellthappback.models.Regime;
import com.lontsi.wellthappback.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.lontsi.wellthappback.config.Utils.REGIME_ENDPOINT;

@RestController(REGIME_ENDPOINT)
public class RegimeController implements RegimeApi {

    @Autowired
    private RegimeService regimeService;

    @Autowired
    private AlimentationService alimentationService;

    @Autowired
    private AlimentProposeService alimentProposeService;

    @Autowired
    private AiService aiService;

    @Autowired
    private UtilisateurService utilisateurService;



    @Override
    public RegimeDto createRegime(RegimeDto regimeDto, String jwt) throws UtilisateurException {
        UtilisateurDto reqUser = utilisateurService.findUserByJwt(jwt);

        RegimeDto regimeDtoCreate = aiService.createRegime(reqUser, regimeDto);

        Regime regimeSave = RegimeDto.toEntity(regimeDtoCreate);

        Alimentation alimentationSave = AlimentationDto.toEntity(regimeDto.getAlimentations().getFirst());
        RegimeDto toReturn = regimeService.createRegime(regimeSave, UtilisateurDto.toEntity(reqUser));
        List<AlimentProposeDto> alimentProposeDtos = new ArrayList<>();
        AlimentationDto alimentationDto = alimentationService.createAlimentation(alimentationSave, regimeSave);
        for (AlimentProposeDto alimentProposeDto : regimeDto.getAlimentations().getFirst().getAlimentsPropose()) {
            alimentProposeDtos.add(alimentProposeService.createAlimentPropose(AlimentProposeDto.toEntity(alimentProposeDto), alimentationSave));
        }
        alimentationDto.setAlimentsPropose(alimentProposeDtos);
        toReturn.setAlimentations(List.of(alimentationDto));

        return toReturn;
    }

    @Override
    public List<RegimeDto> findAllRegimesByUtilisateurId(Integer utilisateurId) {
        return regimeService.findAllRegimesByUtilisateurId(utilisateurId);
    }

    @Override
    public RegimeDto findRegimeById(Integer id) {
        System.out.println("--------------------------------------");
        return regimeService.findRegimeById(id);
    }

    @Override
    public void deleteRegimeById(Integer id) {
        regimeService.deleteRegimeById(id);
    }
}
