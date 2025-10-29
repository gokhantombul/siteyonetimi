package com.gtombul.siteyonetimi.service.demirbas;

import com.gtombul.siteyonetimi.dto.demirbas.DepoDto;
import com.gtombul.siteyonetimi.mapper.demirbas.DepoMapper;
import com.gtombul.siteyonetimi.model.demirbas.Depo;
import com.gtombul.siteyonetimi.repository.demirbas.DepoRepository;
import com.gtombul.siteyonetimi.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DepoServiceImpl extends BaseServiceImpl<Depo, DepoDto, Long> implements DepoService {

    public DepoServiceImpl(DepoRepository repository, DepoMapper mapper) {
        super(repository, mapper, Depo.class);
    }

}
