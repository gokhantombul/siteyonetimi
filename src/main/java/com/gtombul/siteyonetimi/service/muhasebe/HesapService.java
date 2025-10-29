package com.gtombul.siteyonetimi.service.muhasebe;

import com.gtombul.siteyonetimi.dto.muhasebe.EkstreSatirDto;
import com.gtombul.siteyonetimi.dto.muhasebe.HesapDto;
import com.gtombul.siteyonetimi.model.enums.HesapTipi;
import com.gtombul.siteyonetimi.service.base.BaseService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface HesapService extends BaseService<HesapDto, Long> {

    BigDecimal bakiye(Long hesapId);

    List<EkstreSatirDto> ekstre(Long hesapId, LocalDate baslangic, LocalDate bitis);

    HesapDto hesapGetirVeyaOlustur(HesapTipi tip, Long referansId, String ad);

}
