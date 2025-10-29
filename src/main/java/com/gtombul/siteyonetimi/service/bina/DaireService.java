package com.gtombul.siteyonetimi.service.bina;

import com.gtombul.siteyonetimi.dto.bina.DaireDto;
import com.gtombul.siteyonetimi.service.base.BaseService;

import java.util.List;

public interface DaireService extends BaseService<DaireDto, Long> {

    void tamaminiKaydet(List<DaireDto> liste);

}