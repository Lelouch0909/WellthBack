package com.lontsi.wellthappback.controllers;

import com.lontsi.wellthappback.controllers.api.AlimentProposeApi;
import com.lontsi.wellthappback.dto.AlimentProposeDto;
import com.lontsi.wellthappback.dto.AlimentationDto;
import com.lontsi.wellthappback.dto.RegimeDto;
import com.lontsi.wellthappback.dto.UtilisateurDto;
import com.lontsi.wellthappback.exceptions.UtilisateurException;
import com.lontsi.wellthappback.models.AlimentPropose;
import com.lontsi.wellthappback.models.Alimentation;
import com.lontsi.wellthappback.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.lontsi.wellthappback.config.Utils.ALIMENTS_PROPOSE_ENDPOINT;

@RestController(ALIMENTS_PROPOSE_ENDPOINT)
public class AlimentProposeController implements AlimentProposeApi {

    @Autowired
    private AlimentProposeService alimentProposeService;

    @Autowired
    private AiService aiService;

    @Autowired
    private RegimeService regimeService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private AlimentationService alimentationService;

    @Override
    public AlimentProposeDto createAlimentPropose(AlimentProposeDto alimentPropose,Integer alimentationid) throws UtilisateurException {
        AlimentationDto alimentation =alimentationService.findAlimentationById(alimentationid);
        RegimeDto regimeDto = regimeService.findRegimeById(alimentation.getRegime().getId());
        UtilisateurDto reqUser = utilisateurService.findUserById(regimeDto.getUtilisateur().getId());

        return aiService.createAlimentPropose(alimentPropose, alimentation,
                regimeDto, reqUser);
    }

    @Override
    public AlimentProposeDto changerAlimentPropose(AlimentProposeDto alimentPropose, Integer alimentationid) throws UtilisateurException {
        AlimentationDto alimentation =alimentationService.findAlimentationById(alimentationid);

        AlimentProposeDto newAlimentPropose = createAlimentPropose(alimentPropose, alimentationid);
        alimentProposeService.deleteAlimentProposeById(alimentPropose.getId());
        return alimentProposeService.createAlimentPropose(AlimentProposeDto.toEntity(newAlimentPropose), AlimentationDto.toEntity(alimentation));
    }

    @Override
    public List<AlimentProposeDto> findAllAlimentProposeServiceByAlimentationId(Integer alimentationId) {
        return alimentProposeService.findAllAlimentProposeServiceByAlimentationId(alimentationId);
    }


    @Override
    public AlimentProposeDto findAlimentProposeById(Integer id) {
        return alimentProposeService.findAlimentProposeById(id);
    }

    @Override
    public void deleteAlimentProposeById(Integer alimentProposeId) {
        alimentProposeService.deleteAlimentProposeById(alimentProposeId);
    }

    @Override
    public void deleteAllAlimentProposeByAlimentationId(Integer alimentationId) {
        alimentProposeService.deleteAllAlimentProposeByAlimentationId(alimentationId);
    }
}
