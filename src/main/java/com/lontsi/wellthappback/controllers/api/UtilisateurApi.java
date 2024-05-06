package com.lontsi.wellthappback.controllers.api;

import com.lontsi.wellthappback.dto.UtilisateurDto;
import com.lontsi.wellthappback.exceptions.UtilisateurException;
import com.lontsi.wellthappback.models.Utilisateur;
import org.springframework.web.bind.annotation.*;

import static com.lontsi.wellthappback.config.Utils.UTILISATEUR_ENDPOINT;


public interface UtilisateurApi {
    @GetMapping(UTILISATEUR_ENDPOINT +"/find/{jwt}")
    public UtilisateurDto findUserByJwt( @PathVariable String jwt) throws UtilisateurException;

    @PutMapping(UTILISATEUR_ENDPOINT + "/update")
    public  UtilisateurDto updateUtilisateur(@RequestBody(required = true) Utilisateur utilisateur) throws UtilisateurException;

    @DeleteMapping(UTILISATEUR_ENDPOINT + "/delete/{id}")
    public  void deleteUser(@PathVariable Integer id) throws UtilisateurException;

}
