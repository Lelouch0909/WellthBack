package com.lontsi.wellthappback.services.impl;


import com.lontsi.wellthappback.dto.AlimentationDto;
import com.lontsi.wellthappback.dto.RegimeDto;
import com.lontsi.wellthappback.exceptions.EntitiesNotFoundException;
import com.lontsi.wellthappback.exceptions.InvalidOperationException;
import com.lontsi.wellthappback.models.Alimentation;
import com.lontsi.wellthappback.models.Regime;
import com.lontsi.wellthappback.repository.AlimentationRepository;
import com.lontsi.wellthappback.services.AlimentProposeService;
import com.lontsi.wellthappback.services.AlimentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlimentationServiceImpl implements AlimentationService {

    @Autowired
    private AlimentationRepository alimentationRepository;

    @Autowired
    private AlimentProposeService alimentProposeService;

    @Override
    public AlimentationDto createAlimentation(Alimentation alimentation, Regime regime) {
        alimentation.setRegime(regime);
        return AlimentationDto.toDto(alimentationRepository.save(alimentation));
    }



    @Override
    public AlimentationDto changeAlimentation(Alimentation LastAlimentation,Alimentation newAlimentation) {
        RegimeDto regimeDto = findAlimentationById(LastAlimentation.getId()).getRegime();
        deleteAlimentationById(LastAlimentation.getId());
        newAlimentation.setRegime(RegimeDto.toEntity(regimeDto));
        return AlimentationDto.toDto(alimentationRepository.save(newAlimentation));
    }

    @Override
    public AlimentationDto findAlimentationById(Integer alimentationId) {
        Optional<Alimentation> alimentation = alimentationRepository.findById(alimentationId);
        AlimentationDto alimentationDto = AlimentationDto.toDto(Optional.of(alimentation.get())
                .orElseThrow(() ->
                        new EntitiesNotFoundException("aucune alimentation avec l id : " + alimentationId)
                ));
        alimentationDto.setAlimentsPropose(alimentProposeService.findAllAlimentProposeServiceByAlimentationId(alimentationId));
        return alimentationDto;
    }

    @Override
    public void deleteAlimentationById(Integer alimentationId) {
        if (alimentationId != null) {
            alimentationRepository.deleteById(alimentationId);
        }
        throw new InvalidOperationException("impossible de supprimer cette alimentation pas d' id fournit");

    }

    @Override
    public void deleteAllAlimentationByRegimeId(Integer RegimeId) {
        if (RegimeId!=null) {
            try {
                List<AlimentationDto> alimentationDtos = findAllAlimentationsByRegimeId(RegimeId);
                for (AlimentationDto alimentationDto : alimentationDtos) {
                    alimentProposeService.deleteAllAlimentProposeByAlimentationId(alimentationDto.getId());
                }
                alimentationRepository.deleteAllByRegimeId(RegimeId);
            } catch (Exception e) {
                System.out.println("rien ici");
            }
        }
    }

    @Override
    public List<AlimentationDto> findAllAlimentationsByRegimeId(Integer RegimeId) {
        Optional<List<Alimentation>> alimentationsOptional = alimentationRepository.findAllByRegimeIdOrderByCreationDate(RegimeId);
        List<Alimentation> alimentations = Optional.of(alimentationsOptional.get())
                .orElseThrow(() ->
                        new EntitiesNotFoundException("Aucune alimentation trouve pour le regime d id: " + RegimeId));
        return alimentations.stream().map(AlimentationDto::toDto).collect(Collectors.toList());
    }
}
