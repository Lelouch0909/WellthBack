package com.lontsi.wellthappback.repository;

import com.lontsi.wellthappback.models.Alimentation;
import com.lontsi.wellthappback.models.Regime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlimentationRepository extends JpaRepository<Alimentation,Integer> {
    Optional<List<Alimentation>> findAllByRegimeIdOrderByCreationDate(Integer idRegime);
    void deleteAllByRegimeId(Integer idUtilisateur);
}
