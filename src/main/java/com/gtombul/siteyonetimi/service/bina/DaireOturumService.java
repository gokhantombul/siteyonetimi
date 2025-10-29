package com.gtombul.siteyonetimi.service.bina;

import com.gtombul.siteyonetimi.dto.bina.DaireOturumDto;
import com.gtombul.siteyonetimi.service.base.BaseService;

import java.time.LocalDate;
import java.util.List;

public interface DaireOturumService extends BaseService<DaireOturumDto, Long> {

    List<DaireOturumDto> aktifOturumlarByDaire(Long daireId, LocalDate tarih);

    List<DaireOturumDto> aktifOturumlarByKisi(Long kisiId, LocalDate tarih);

    void tamaminiKaydet(List<DaireOturumDto> liste);

}