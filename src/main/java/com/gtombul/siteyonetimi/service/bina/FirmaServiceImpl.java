package com.gtombul.siteyonetimi.service.bina;

import com.gtombul.siteyonetimi.dto.bina.FirmaDto;
import com.gtombul.siteyonetimi.mapper.bina.FirmaMapper;
import com.gtombul.siteyonetimi.model.bina.Firma;
import com.gtombul.siteyonetimi.repository.bina.FirmaRepository;
import com.gtombul.siteyonetimi.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FirmaServiceImpl extends BaseServiceImpl<Firma, FirmaDto, Long> implements FirmaService {

    public FirmaServiceImpl(FirmaRepository repository, FirmaMapper mapper) {
        super(repository, mapper, Firma.class);
    }

    @Override
    public void tamaminiKaydet(List<FirmaDto> liste) {
        repository.saveAll(mapper.toEntity(liste));
    }

}
