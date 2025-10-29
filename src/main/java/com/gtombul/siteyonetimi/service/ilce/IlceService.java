package com.gtombul.siteyonetimi.service.ilce;

import com.gtombul.siteyonetimi.dto.adres.ilce.IlceDto;
import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.service.base.BaseService;

import java.util.List;

public interface IlceService extends BaseService<IlceDto, Long> {

    void tamaminiKaydet(List<IlceDto> liste);

    List<IlceDto> findByIlIdAndDurum(Long ilId, Durum durum);

}
