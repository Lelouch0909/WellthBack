package com.lontsi.wellthappback.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lontsi.wellthappback.models.Utilisateur;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class UtilisateurDto {


    private Integer id;

    private Instant creationDate;


    private Instant lastModifiedDate;

    private String nom;

    private String prenom;

    private String email;


    private Integer age;

    private String sexe;

    private String motDePasse;


    private String photo;

    private Float poids;

    private Float taille;



    @JsonIgnore
    private List<RegimeDto> regimeDtos;


    private String antecedentsMedicaux;


    public static UtilisateurDto toDto(Utilisateur utilisateur) {
        UtilisateurDto dto = new UtilisateurDto();
        dto.setId(utilisateur.getId());
        dto.setCreationDate(utilisateur.getCreationDate());
        dto.setLastModifiedDate(utilisateur.getLastModifiedDate());
        dto.setNom(utilisateur.getNom());
        dto.setPrenom(utilisateur.getPrenom());
        dto.setEmail(utilisateur.getEmail());
        dto.setAge(utilisateur.getAge());
        dto.setSexe(utilisateur.getSexe());
        dto.setMotDePasse(utilisateur.getMotDePasse());
        dto.setPhoto(utilisateur.getPhoto());
        dto.setPoids(utilisateur.getPoids());
        dto.setTaille(utilisateur.getTaille());
        dto.setAntecedentsMedicaux(utilisateur.getAntecedentsMedicaux());
        return dto;
    }

    public static Utilisateur toEntity(UtilisateurDto dto) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(dto.getId());
        utilisateur.setCreationDate(dto.getCreationDate());
        utilisateur.setLastModifiedDate(dto.getLastModifiedDate());
        utilisateur.setNom(dto.getNom());
        utilisateur.setPrenom(dto.getPrenom());
        utilisateur.setEmail(dto.getEmail());
        utilisateur.setAge(dto.getAge());
        utilisateur.setSexe(dto.getSexe());
        utilisateur.setMotDePasse(dto.getMotDePasse());
        utilisateur.setPhoto(dto.getPhoto());
        utilisateur.setPoids(dto.getPoids());
        utilisateur.setTaille(dto.getTaille());
        utilisateur.setAntecedentsMedicaux(dto.getAntecedentsMedicaux());

        return utilisateur;
    }


}
