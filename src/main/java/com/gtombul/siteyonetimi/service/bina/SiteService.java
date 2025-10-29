package com.gtombul.siteyonetimi.service.bina;

import com.gtombul.siteyonetimi.dto.bina.SiteDto;
import com.gtombul.siteyonetimi.service.base.BaseService;

import java.util.List;

public interface SiteService extends BaseService<SiteDto, Long> {

    void tamaminiKaydet(List<SiteDto> liste);

}