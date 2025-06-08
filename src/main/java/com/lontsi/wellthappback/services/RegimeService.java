package com.lontsi.wellthappback.services;

import com.lontsi.wellthappback.dto.RegimeDto;
import com.lontsi.wellthappback.dto.UtilisateurDto;
import com.lontsi.wellthappback.exceptions.UtilisateurException;
import com.lontsi.wellthappback.models.Regime;
import com.lontsi.wellthappback.models.Utilisateur;

import java.util.List;

public interface RegimeService {

    public  RegimeDto createRegime (Regime regime, Utilisateur reqUser) ;

    public List<RegimeDto> findAllRegimesByUtilisateurId(Integer utilisateurId);

    public RegimeDto findRegimeById(Integer RegimeId);


    public  void deleteRegimeById(Integer RegimeId);

    public  void  deleteAllRegimesByUtilisateurId(Integer UtilisateurId);

}
