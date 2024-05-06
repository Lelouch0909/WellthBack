package com.lontsi.wellthappback.repository;

import com.lontsi.wellthappback.dto.AlimentProposeDto;
import com.lontsi.wellthappback.models.AlimentPropose;
import com.lontsi.wellthappback.models.Alimentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlimentProposeRepository extends JpaRepository<AlimentPropose,Integer> {
    Optional<List<AlimentPropose>> findAllByAlimentationIdOrderByCreationDate(Integer idAlimentation);
    void deleteAllByAlimentationId(Integer idAlimentation);
}
