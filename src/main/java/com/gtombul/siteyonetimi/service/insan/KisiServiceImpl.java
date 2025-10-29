package com.gtombul.siteyonetimi.service.insan;

import com.gtombul.siteyonetimi.dto.insan.KisiDto;
import com.gtombul.siteyonetimi.mapper.insan.KisiMapper;
import com.gtombul.siteyonetimi.model.insan.Kisi;
import com.gtombul.siteyonetimi.repository.insan.KisiRepository;
import com.gtombul.siteyonetimi.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class KisiServiceImpl extends BaseServiceImpl<Kisi, KisiDto, Long> implements KisiService {

    public KisiServiceImpl(KisiRepository repository, KisiMapper mapper) {
        super(repository, mapper, Kisi.class);
    }

}
