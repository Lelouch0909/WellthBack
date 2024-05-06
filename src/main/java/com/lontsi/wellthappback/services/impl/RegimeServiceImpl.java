package com.lontsi.wellthappback.services.impl;

import com.lontsi.wellthappback.dto.RegimeDto;
import com.lontsi.wellthappback.exceptions.EntitiesNotFoundException;
import com.lontsi.wellthappback.exceptions.InvalidOperationException;
import com.lontsi.wellthappback.models.Regime;
import com.lontsi.wellthappback.models.Utilisateur;
import com.lontsi.wellthappback.repository.RegimeRepository;
import com.lontsi.wellthappback.services.AlimentationService;
import com.lontsi.wellthappback.services.RegimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RegimeServiceImpl implements RegimeService {

    @Autowired
    private RegimeRepository regimeRepository;

    @Autowired
    private AlimentationService alimentationService;

    @Override
    public RegimeDto findRegimeById(Integer regimeId) {
        Optional<Regime> regime = regimeRepository.findById(regimeId);
        RegimeDto regimeDto = RegimeDto.toDto(Optional.of(regime.get())
                .orElseThrow(() ->
                        new EntitiesNotFoundException("aucun regime avec l id : " + regimeId)
                ));
        regimeDto.setAlimentations(alimentationService.findAllAlimentationsByRegimeId(regimeId));
        return regimeDto;
    }

    @Override
    public void deleteRegimeById(Integer regimeId) {
        if (regimeId != null) {
            regimeRepository.deleteById(regimeId);
        } else {
            throw new InvalidOperationException("impossible de supprimer ce regime pas d' id fournit");

        }
    }

    @Override
    public void deleteAllRegimesByUtilisateurId(Integer utilisateurId) {
        if (utilisateurId != null) {
            try {
                List<RegimeDto> regimeDtos = findAllRegimesByUtilisateurId(utilisateurId);
                for (RegimeDto regimeDto : regimeDtos) {
                    alimentationService.deleteAllAlimentationByRegimeId(regimeDto.getId());
                }
                regimeRepository.deleteAllByUtilisateurId(utilisateurId);
            } catch (Exception e) {
                System.out.println("rien ici non plus");
            }
        }
        throw new InvalidOperationException("impossible de supprimer ce regime pas d' id fournit");

    }

    @Override
    public List<RegimeDto> findAllRegimesByUtilisateurId(Integer utilisateurId) {
        Optional<List<Regime>> regimesOptional = regimeRepository.findAllByUtilisateurIdOrderByCreationDate(utilisateurId);
        List<Regime> regimes = Optional.of(regimesOptional.get()).orElseThrow(() -> new EntitiesNotFoundException("Aucun regime trouve pour cet utilisateur possedant l id :" + utilisateurId));
        return regimes.stream().map(RegimeDto::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public RegimeDto createRegime(Regime regime, Utilisateur reqUser) {
        regime.setUtilisateur(reqUser);
        return RegimeDto.toDto(regimeRepository.save(regime));

    }

}
