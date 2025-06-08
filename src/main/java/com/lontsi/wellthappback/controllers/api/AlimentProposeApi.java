package com.lontsi.wellthappback.controllers.api;

import com.lontsi.wellthappback.dto.AlimentProposeDto;
import com.lontsi.wellthappback.exceptions.UtilisateurException;
import com.lontsi.wellthappback.models.AlimentPropose;
import com.lontsi.wellthappback.models.Alimentation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lontsi.wellthappback.config.Utils.ALIMENTATION_ENDPOINT;
import static com.lontsi.wellthappback.config.Utils.ALIMENTS_PROPOSE_ENDPOINT;

public interface AlimentProposeApi {

    @GetMapping(ALIMENTS_PROPOSE_ENDPOINT + "/findall/{alimentationId}")
    public List<AlimentProposeDto> findAllAlimentProposeServiceByAlimentationId(@RequestParam Integer alimentationId);


    @PostMapping(ALIMENTS_PROPOSE_ENDPOINT + "/createalimentproposeforalimentationid/{alimentationid}")
    public AlimentProposeDto createAlimentPropose(@RequestBody AlimentProposeDto alimentPropose,@RequestParam Integer alimentationid) throws UtilisateurException;


    @PostMapping(ALIMENTS_PROPOSE_ENDPOINT + "/changerlimentproposeforalimentationid/{alimentationid}")
    public AlimentProposeDto changerAlimentPropose(@RequestBody AlimentProposeDto alimentPropose, @RequestParam Integer alimentationid) throws UtilisateurException;


    @GetMapping(ALIMENTS_PROPOSE_ENDPOINT + "/find/{id}")
    public AlimentProposeDto findAlimentProposeById(@RequestParam Integer id);


    @DeleteMapping(ALIMENTS_PROPOSE_ENDPOINT + "/delete/{id}")
    public void deleteAlimentProposeById(Integer alimentProposeId);

    @DeleteMapping(ALIMENTS_PROPOSE_ENDPOINT + "/deleteall/{alimentationId}")
    public void deleteAllAlimentProposeByAlimentationId(@RequestParam Integer alimentationId);
}
