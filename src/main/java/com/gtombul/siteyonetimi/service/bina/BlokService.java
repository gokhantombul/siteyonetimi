package com.gtombul.siteyonetimi.service.bina;

import com.gtombul.siteyonetimi.dto.bina.BlokDto;
import com.gtombul.siteyonetimi.service.base.BaseService;

import java.util.List;

public interface BlokService extends BaseService<BlokDto, Long> {

    void tamaminiKaydet(List<BlokDto> liste);

}