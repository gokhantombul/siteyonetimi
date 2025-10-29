package com.gtombul.siteyonetimi.service.bina;

import com.gtombul.siteyonetimi.dto.bina.KiraSozlesmesiDto;
import com.gtombul.siteyonetimi.service.base.BaseService;

import java.time.LocalDate;
import java.util.List;

public interface KiraSozlesmesiService extends BaseService<KiraSozlesmesiDto, Long> {

    void tamaminiKaydet(List<KiraSozlesmesiDto> liste);

    List<KiraSozlesmesiDto> aktifSozlesmeler(Long daireId, LocalDate tarih);

}