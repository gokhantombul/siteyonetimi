package com.gtombul.siteyonetimi.service.banka;

import com.gtombul.siteyonetimi.dto.banka.BankaDto;
import com.gtombul.siteyonetimi.mapper.banka.BankaMapper;
import com.gtombul.siteyonetimi.model.banka.Banka;
import com.gtombul.siteyonetimi.repository.banka.BankaRepository;
import com.gtombul.siteyonetimi.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankaServiceImpl extends BaseServiceImpl<Banka, BankaDto, Long> implements BankaService {

    public BankaServiceImpl(BankaRepository repository, BankaMapper mapper) {
        super(repository, mapper, Banka.class);
    }

    @Override
    public void tamaminiKaydet(List<BankaDto> bankaDtoList) {
        repository.saveAll(mapper.toEntity(bankaDtoList));
    }

}