package com.gtombul.siteyonetimi.service.arac;

import com.gtombul.siteyonetimi.dto.arac.AracDto;
import com.gtombul.siteyonetimi.mapper.arac.AracMapper;
import com.gtombul.siteyonetimi.model.arac.Arac;
import com.gtombul.siteyonetimi.repository.arac.AracRepository;
import com.gtombul.siteyonetimi.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AracServiceImpl extends BaseServiceImpl<Arac, AracDto, Long> implements AracService {

    public AracServiceImpl(AracRepository repository, AracMapper mapper) {
        super(repository, mapper, Arac.class);
    }

    @Override
    public void tamaminiKaydet(List<AracDto> liste) {
        repository.saveAll(mapper.toEntity(liste));
    }

}