package com.lontsi.wellthappback.services;

import com.lontsi.wellthappback.dto.UtilisateurDto;
import com.lontsi.wellthappback.exceptions.UtilisateurException;
import com.lontsi.wellthappback.models.Utilisateur;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.lontsi.wellthappback.config.Utils.UTILISATEUR_ENDPOINT;

public interface UtilisateurService {


    public UtilisateurDto findUserById(Integer userId) throws UtilisateurException;

    public UtilisateurDto findUserByJwt(String jwt) throws UtilisateurException;

    public  UtilisateurDto createUtilisateur (Utilisateur utilisateur) throws UtilisateurException;

    public  UtilisateurDto updateUtilisateur(Utilisateur utilisateur) throws UtilisateurException;
    public  UtilisateurDto findUserByEmail(String email) throws UtilisateurException;

    public  void deleteUser(Integer userId) throws UtilisateurException;

}
