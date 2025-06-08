package com.lontsi.wellthappback.services;

import com.lontsi.wellthappback.dto.AlimentProposeDto;
import com.lontsi.wellthappback.models.AlimentPropose;
import com.lontsi.wellthappback.models.Alimentation;
import com.lontsi.wellthappback.models.Regime;

import java.util.List;

public interface AlimentProposeService {

    public List<AlimentProposeDto> findAllAlimentProposeServiceByAlimentationId (Integer alimentationId);


    public AlimentProposeDto createAlimentPropose(AlimentPropose alimentPropose, Alimentation alimentation);


    public AlimentProposeDto findAlimentProposeById (Integer alimentationProposeId);


    public void deleteAlimentProposeById(Integer alimentProposeId);

    public void deleteAllAlimentProposeByAlimentationId(Integer alimentationId);

}
