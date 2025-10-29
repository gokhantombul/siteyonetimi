package com.gtombul.siteyonetimi.service.muhasebe;

import com.gtombul.siteyonetimi.dto.muhasebe.AidatPlaniDto;
import com.gtombul.siteyonetimi.service.base.BaseService;

import java.time.YearMonth;

public interface AidatPlaniService extends BaseService<AidatPlaniDto, Long> {

    AidatPlaniDto olustur(AidatPlaniDto dto);

    void donemIcinAidatOlustur(Long planId, YearMonth donem);

    void tumPlanlarIcinAidatOlustur(YearMonth donem);

}
