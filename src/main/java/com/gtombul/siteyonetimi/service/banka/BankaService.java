package com.gtombul.siteyonetimi.service.banka;

import com.gtombul.siteyonetimi.dto.banka.BankaDto;
import com.gtombul.siteyonetimi.service.base.BaseService;

import java.util.List;

public interface BankaService extends BaseService<BankaDto, Long> {

    void tamaminiKaydet(List<BankaDto> bankaDtoList);

}
