package com.gtombul.siteyonetimi.service.bina;

import com.gtombul.siteyonetimi.dto.bina.BlokDto;
import com.gtombul.siteyonetimi.mapper.bina.BlokMapper;
import com.gtombul.siteyonetimi.model.bina.Blok;
import com.gtombul.siteyonetimi.repository.bina.BlokRepository;
import com.gtombul.siteyonetimi.service.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BlokServiceImpl extends BaseServiceImpl<Blok, BlokDto, Long> implements BlokService {

    public BlokServiceImpl(BlokRepository repository, BlokMapper mapper) {
        super(repository, mapper, Blok.class);
    }

    @Override
    public void tamaminiKaydet(List<BlokDto> liste) {
        repository.saveAll(mapper.toEntity(liste));
    }

}
