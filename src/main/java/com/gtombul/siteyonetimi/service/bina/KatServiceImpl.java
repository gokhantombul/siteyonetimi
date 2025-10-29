package com.gtombul.siteyonetimi.service.bina;

import com.gtombul.siteyonetimi.dto.bina.KatDto;
import com.gtombul.siteyonetimi.mapper.bina.KatMapper;
import com.gtombul.siteyonetimi.model.bina.Kat;
import com.gtombul.siteyonetimi.repository.bina.KatRepository;
import com.gtombul.siteyonetimi.service.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class KatServiceImpl extends BaseServiceImpl<Kat, KatDto, Long> implements KatService {

    public KatServiceImpl(KatRepository repository, KatMapper mapper) {
        super(repository, mapper, Kat.class);
    }

    @Override
    public void tamaminiKaydet(List<KatDto> liste) {
        repository.saveAll(mapper.toEntity(liste));
    }

}
