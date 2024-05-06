package com.lontsi.wellthappback.repository;

import com.lontsi.wellthappback.models.Regime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegimeRepository extends JpaRepository<Regime ,Integer> {


    Optional<List<Regime>> findAllByUtilisateurIdOrderByCreationDate(Integer idUtilisateur);
    void deleteAllByUtilisateurId(Integer idUtilisateur);

}
