package com.gtombul.siteyonetimi.service.demirbas;

import com.gtombul.siteyonetimi.dto.demirbas.DemirbasKategoriDto;
import com.gtombul.siteyonetimi.mapper.demirbas.DemirbasKategoriMapper;
import com.gtombul.siteyonetimi.model.demirbas.DemirbasKategori;
import com.gtombul.siteyonetimi.repository.demirbas.DemirbasKategoriRepository;
import com.gtombul.siteyonetimi.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DemirbasKategoriServiceImpl extends BaseServiceImpl<DemirbasKategori, DemirbasKategoriDto, Long>
        implements DemirbasKategoriService {

    public DemirbasKategoriServiceImpl(DemirbasKategoriRepository repository, DemirbasKategoriMapper mapper) {
        super(repository, mapper, DemirbasKategori.class);
    }

}
