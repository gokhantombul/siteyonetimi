package com.gtombul.siteyonetimi.service.bina;

import com.gtombul.siteyonetimi.dto.bina.KatDto;
import com.gtombul.siteyonetimi.service.base.BaseService;

import java.util.List;

public interface KatService extends BaseService<KatDto, Long> {

    void tamaminiKaydet(List<KatDto> liste);

}
