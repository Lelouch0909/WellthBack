package com.lontsi.wellthappback.controllers;

import com.lontsi.wellthappback.controllers.api.UtilisateurApi;
import com.lontsi.wellthappback.dto.UtilisateurDto;
import com.lontsi.wellthappback.exceptions.UtilisateurException;
import com.lontsi.wellthappback.models.Utilisateur;
import com.lontsi.wellthappback.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import static com.lontsi.wellthappback.config.Utils.UTILISATEUR_ENDPOINT;

@RestController(UTILISATEUR_ENDPOINT)
public class UtilisateurController implements UtilisateurApi {

    @Autowired
    private UtilisateurService utilisateurService;

    @Override
    public UtilisateurDto findUserByJwt(String jwt) throws UtilisateurException {
        return utilisateurService.findUserByJwt(jwt);
    }


    @Override
    public UtilisateurDto updateUtilisateur(Utilisateur utilisateur) throws UtilisateurException {
        return utilisateurService.updateUtilisateur(utilisateur);
    }


    @Override
    public void deleteUser(Integer userId) throws UtilisateurException {
        utilisateurService.deleteUser(userId);
    }
}
