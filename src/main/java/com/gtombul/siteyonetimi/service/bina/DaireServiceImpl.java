package com.gtombul.siteyonetimi.service.bina;

import com.gtombul.siteyonetimi.dto.bina.DaireDto;
import com.gtombul.siteyonetimi.mapper.bina.DaireMapper;
import com.gtombul.siteyonetimi.model.bina.Daire;
import com.gtombul.siteyonetimi.repository.bina.DaireRepository;
import com.gtombul.siteyonetimi.service.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DaireServiceImpl extends BaseServiceImpl<Daire, DaireDto, Long> implements DaireService {

    public DaireServiceImpl(DaireRepository repository, DaireMapper mapper) {
        super(repository, mapper, Daire.class);
    }

    @Override
    public void tamaminiKaydet(List<DaireDto> liste) {
        repository.saveAll(mapper.toEntity(liste));
    }

}
