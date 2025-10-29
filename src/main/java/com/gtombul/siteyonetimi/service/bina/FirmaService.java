package com.gtombul.siteyonetimi.service.bina;

import com.gtombul.siteyonetimi.dto.bina.FirmaDto;
import com.gtombul.siteyonetimi.service.base.BaseService;

import java.util.List;

public interface FirmaService extends BaseService<FirmaDto, Long> {

    void tamaminiKaydet(List<FirmaDto> liste);

}
