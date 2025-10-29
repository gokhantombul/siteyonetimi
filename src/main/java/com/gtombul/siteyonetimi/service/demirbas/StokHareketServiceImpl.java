package com.gtombul.siteyonetimi.service.demirbas;

import com.gtombul.siteyonetimi.dto.demirbas.StokHareketDto;
import com.gtombul.siteyonetimi.mapper.demirbas.StokHareketMapper;
import com.gtombul.siteyonetimi.model.demirbas.StokHareket;
import com.gtombul.siteyonetimi.repository.demirbas.StokHareketRepository;
import com.gtombul.siteyonetimi.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class StokHareketServiceImpl extends BaseServiceImpl<StokHareket, StokHareketDto, Long> implements StokHareketService {

    public StokHareketServiceImpl(StokHareketRepository repository, StokHareketMapper mapper) {
        super(repository, mapper, StokHareket.class);
    }

}