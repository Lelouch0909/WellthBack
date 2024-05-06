package com.lontsi.wellthappback.services.impl;

import com.lontsi.wellthappback.config.JwtProvider;
import com.lontsi.wellthappback.dto.UtilisateurDto;
import com.lontsi.wellthappback.exceptions.UtilisateurException;
import com.lontsi.wellthappback.models.Utilisateur;
import com.lontsi.wellthappback.repository.UtilisateurRepository;
import com.lontsi.wellthappback.services.RegimeService;
import com.lontsi.wellthappback.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private RegimeService regimeService;


    @Override
    public UtilisateurDto findUserById(Integer userId) throws UtilisateurException {
        Utilisateur utilisateur = utilisateurRepository.findById(userId).orElseThrow(() -> new UtilisateurException("aucun utilisateur trouve avec l id:" + userId));
        UtilisateurDto utilisateurDto = UtilisateurDto.toDto(utilisateur);
        utilisateurDto.setRegimeDtos(regimeService.findAllRegimesByUtilisateurId(userId));
        return utilisateurDto;
    }

    @Override
    public UtilisateurDto findUserByJwt(String jwt) throws UtilisateurException {
        String email = jwtProvider.getEmailFromToken(jwt);
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email);
        if (utilisateur != null) {
            UtilisateurDto utilisateurDto = UtilisateurDto.toDto(utilisateur);
            utilisateurDto.setRegimeDtos(regimeService.findAllRegimesByUtilisateurId(utilisateur.getId()));
            return utilisateurDto;
        }
        throw new UtilisateurException("Aucun utilisateur avec l email fournie par le token :" + email);


    }

    @Override
    public UtilisateurDto createUtilisateur(Utilisateur utilisateur) throws UtilisateurException {

        Utilisateur test = utilisateurRepository.findByEmail(utilisateur.getEmail());
        if (test != null) {
            throw new UtilisateurException("Un utilisateur existe deja avec cet email " + utilisateur.getEmail());
        }

        return UtilisateurDto.toDto(utilisateurRepository.save(utilisateur));

    }

    @Override
    public UtilisateurDto updateUtilisateur(Utilisateur newUser) throws UtilisateurException {

        try {
            UtilisateurDto lastUser = findUserByEmail(newUser.getEmail());
            if (!Objects.equals(newUser.getAntecedentsMedicaux(), lastUser.getAntecedentsMedicaux()) && newUser.getAntecedentsMedicaux() != null) {
                lastUser.setAntecedentsMedicaux(newUser.getAntecedentsMedicaux());
            }
            if (!Objects.equals(newUser.getPhoto(), lastUser.getPhoto()) && newUser.getPhoto() != null) {
                lastUser.setPhoto(newUser.getPhoto());
            }
            if (!Objects.equals(newUser.getNom(), lastUser.getNom()) && newUser.getNom() != null) {
                lastUser.setPhoto(newUser.getNom());
            }

            if (!Objects.equals(newUser.getTaille(), lastUser.getTaille()) && newUser.getTaille() != null) {
                lastUser.setTaille(newUser.getTaille());
            }
            if (!Objects.equals(newUser.getPoids(), lastUser.getPoids()) && newUser.getPoids() != null) {
                lastUser.setPoids(newUser.getPoids());
            }
            if (!Objects.equals(newUser.getMotDePasse(), lastUser.getMotDePasse()) && newUser.getMotDePasse() != null) {
                lastUser.setMotDePasse(newUser.getMotDePasse());
            }
            if (!Objects.equals(newUser.getSexe(), lastUser.getSexe()) && newUser.getSexe() != null) {
                lastUser.setSexe(newUser.getSexe());
            }
            if (!Objects.equals(newUser.getAge(), lastUser.getAge()) && newUser.getAge() != null) {
                lastUser.setAge(newUser.getAge());
            }
            if (!Objects.equals(newUser.getEmail(), lastUser.getEmail()) && newUser.getEmail() != null) {
                lastUser.setEmail(newUser.getEmail());
            }
            if (!Objects.equals(newUser.getPrenom(), lastUser.getPrenom()) && newUser.getPrenom() != null) {
                lastUser.setPrenom(newUser.getPrenom());
            }

            return UtilisateurDto.toDto(utilisateurRepository.save(UtilisateurDto.toEntity(lastUser)));
        } catch (UtilisateurException exception) {
            throw new UtilisateurException("impossible de mettre a jour les info de cet utilisateur il n existe pas d utilisateur avec le mail:" + newUser.getEmail());
        }

    }


    @Override
    public UtilisateurDto findUserByEmail(String email) throws UtilisateurException {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email);
        if (utilisateur != null) {

            UtilisateurDto utilisateurDto = UtilisateurDto.toDto(utilisateur);

            utilisateurDto.setRegimeDtos(regimeService.findAllRegimesByUtilisateurId(utilisateur.getId()));

            return utilisateurDto;
        }
        throw new UtilisateurException("Aucun utilisateur avec l email :" + email);


    }

    @Override
    public void deleteUser(Integer userId) throws UtilisateurException {
        Utilisateur utilisateur = utilisateurRepository.findById(userId).orElseThrow(() -> new UtilisateurException("Impossible de supprimer l utilisateur aucun utilisateur trouve avec l id:" + userId));
        regimeService.deleteAllRegimesByUtilisateurId(userId);
        utilisateurRepository.deleteById(userId);
    }

}
