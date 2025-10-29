package com.gtombul.siteyonetimi.service.il;

import com.gtombul.siteyonetimi.dto.adres.il.IlDto;
import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.service.base.BaseService;

import java.util.List;
import java.util.Optional;

public interface IlService extends BaseService<IlDto, Long> {

    void tamaminiKaydet(List<IlDto> liste);

    Optional<IlDto> findByPlakaAndDurum(int plaka, Durum durum);

}
