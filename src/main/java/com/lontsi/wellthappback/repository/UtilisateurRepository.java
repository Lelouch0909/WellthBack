package com.lontsi.wellthappback.repository;

import com.lontsi.wellthappback.models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur ,Integer> {

    public  Utilisateur findByEmail(String email);

}
