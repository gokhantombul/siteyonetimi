package com.gtombul.siteyonetimi.service.ilce;

import com.gtombul.siteyonetimi.dto.adres.ilce.IlceDto;
import com.gtombul.siteyonetimi.mapper.ilce.IlceMapper;
import com.gtombul.siteyonetimi.model.adres.Ilce;
import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.repository.ilce.IlceRepository;
import com.gtombul.siteyonetimi.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IlceServiceImpl extends BaseServiceImpl<Ilce, IlceDto, Long> implements IlceService {

    private final IlceRepository ilceRepository;
    private final IlceMapper ilceMapper;

    protected IlceServiceImpl(IlceRepository repository, IlceMapper mapper) {
        super(repository, mapper, Ilce.class);
        this.ilceRepository = repository;
        this.ilceMapper = mapper;
    }

    @Override
    public void tamaminiKaydet(List<IlceDto> liste) {
        repository.saveAll(mapper.toEntity(liste));
    }

    @Override
    public List<IlceDto> findByIlIdAndDurum(Long ilId, Durum durum) {
        return ilceRepository.findByIlIdAndDurum(ilId, durum).stream().map(ilceMapper::toDto).toList();
    }

}
