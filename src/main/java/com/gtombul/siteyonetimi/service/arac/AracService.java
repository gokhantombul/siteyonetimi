package com.gtombul.siteyonetimi.service.arac;

import com.gtombul.siteyonetimi.dto.arac.AracDto;
import com.gtombul.siteyonetimi.service.base.BaseService;

import java.util.List;

public interface AracService extends BaseService<AracDto, Long> {

    void tamaminiKaydet(List<AracDto> liste);

}