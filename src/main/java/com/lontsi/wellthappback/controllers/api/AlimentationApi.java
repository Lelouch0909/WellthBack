package com.lontsi.wellthappback.controllers.api;

import com.lontsi.wellthappback.dto.AlimentationDto;
import com.lontsi.wellthappback.dto.RegimeDto;
import com.lontsi.wellthappback.exceptions.UtilisateurException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lontsi.wellthappback.config.Utils.ALIMENTATION_ENDPOINT;

public interface AlimentationApi {



    @PostMapping(ALIMENTATION_ENDPOINT +"/create")
    public AlimentationDto createAlimentation(@RequestBody RegimeDto regime,@RequestHeader(name = "Authorization") String jwt) throws UtilisateurException;

    @GetMapping(ALIMENTATION_ENDPOINT + "/changealimentationofregimeid/{regimeid}")
    public  AlimentationDto changerAlimentation(@RequestBody AlimentationDto alimentationDto,@PathVariable("regimeid") Integer regimeid, @RequestHeader(name = "Authorization") String jwt) throws UtilisateurException;


    @GetMapping(ALIMENTATION_ENDPOINT +"/find/{id}")
    public AlimentationDto findAlimentationById (@PathVariable("id") Integer id);



    @GetMapping(ALIMENTATION_ENDPOINT +"/findall/{id}")
    public List<AlimentationDto> findAllAlimentationsByRegimeId (@PathVariable("id") Integer id);

}
