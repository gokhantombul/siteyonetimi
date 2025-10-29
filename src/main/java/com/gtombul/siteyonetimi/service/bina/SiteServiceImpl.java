package com.gtombul.siteyonetimi.service.bina;

import com.gtombul.siteyonetimi.dto.bina.SiteDto;
import com.gtombul.siteyonetimi.mapper.bina.SiteMapper;
import com.gtombul.siteyonetimi.model.bina.Site;
import com.gtombul.siteyonetimi.repository.bina.SiteRepository;
import com.gtombul.siteyonetimi.service.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SiteServiceImpl extends BaseServiceImpl<Site, SiteDto, Long> implements SiteService {

    public SiteServiceImpl(SiteRepository repository, SiteMapper mapper) {
        super(repository, mapper, Site.class);
    }

    @Override
    public void tamaminiKaydet(List<SiteDto> liste) {
        repository.saveAll(mapper.toEntity(liste));
    }

}
