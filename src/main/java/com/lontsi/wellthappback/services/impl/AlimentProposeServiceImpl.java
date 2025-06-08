package com.lontsi.wellthappback.services.impl;

import com.lontsi.wellthappback.dto.AlimentProposeDto;
import com.lontsi.wellthappback.dto.RegimeDto;
import com.lontsi.wellthappback.exceptions.EntitiesNotFoundException;
import com.lontsi.wellthappback.exceptions.InvalidOperationException;
import com.lontsi.wellthappback.models.AlimentPropose;
import com.lontsi.wellthappback.models.Alimentation;
import com.lontsi.wellthappback.models.Regime;
import com.lontsi.wellthappback.repository.AlimentProposeRepository;
import com.lontsi.wellthappback.services.AlimentProposeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlimentProposeServiceImpl implements AlimentProposeService {

    @Autowired
    private AlimentProposeRepository alimentProposeRepository;

    @Override
    public List<AlimentProposeDto> findAllAlimentProposeServiceByAlimentationId(Integer alimentationId) {
        Optional<List<AlimentPropose>> alimentProposesOptional = alimentProposeRepository.findAllByAlimentationIdOrderByCreationDate(alimentationId);
        List<AlimentPropose> alimentProposes = Optional.of(alimentProposesOptional.get()).orElseThrow(()-> new EntitiesNotFoundException("Aucun alimentProposes trouve pour cette alimentation possedant l id :" +alimentationId));
        return alimentProposes.stream().map(AlimentProposeDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AlimentProposeDto createAlimentPropose(AlimentPropose alimentPropose, Alimentation alimentation) {
        alimentPropose.setAlimentation(alimentation);
        return AlimentProposeDto.toDto( alimentProposeRepository.save(alimentPropose));
    }

    @Override
    public AlimentProposeDto findAlimentProposeById(Integer alimentationProposeId) {
        Optional<AlimentPropose> alimentPropose = alimentProposeRepository.findById(alimentationProposeId);
        return AlimentProposeDto.toDto(Optional.of(alimentPropose.get())
                .orElseThrow(()->
                        new EntitiesNotFoundException("aucun alimentPropose avec l id : "+alimentationProposeId)
                ));
    }

    @Override
    public void deleteAlimentProposeById(Integer alimentationProposeId) {
        if (alimentationProposeId !=null){
            alimentProposeRepository.deleteById(alimentationProposeId);
        }
        throw new InvalidOperationException("impossible de supprimer cet alimentPropose pas d' id fournit");

    }

    @Override
    public void deleteAllAlimentProposeByAlimentationId(Integer alimentationId) {

            alimentProposeRepository.deleteAllByAlimentationId(alimentationId);
    }
}
