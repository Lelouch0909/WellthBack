package com.lontsi.wellthappback.controllers.api;

import com.lontsi.wellthappback.dto.RegimeDto;
import com.lontsi.wellthappback.exceptions.UtilisateurException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lontsi.wellthappback.config.Utils.REGIME_ENDPOINT;

public interface RegimeApi {


    @PostMapping(REGIME_ENDPOINT + "/create")
    public RegimeDto createRegime(@RequestBody RegimeDto regime,@RequestHeader(name = "Authorization") String jwt) throws UtilisateurException;

    @GetMapping(REGIME_ENDPOINT + "/findAll/{id}")
    public List<RegimeDto> findAllRegimesByUtilisateurId(@PathVariable("id") Integer id);

    @GetMapping(REGIME_ENDPOINT + "/find/{id}")
    public RegimeDto findRegimeById(@PathVariable("id") Integer id);

    @DeleteMapping(REGIME_ENDPOINT + "/delete/{id}")
    public void deleteRegimeById(@PathVariable("id") Integer id);

}
