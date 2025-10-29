package com.gtombul.siteyonetimi.service.anket;

import com.gtombul.siteyonetimi.dto.anket.*;
import com.gtombul.siteyonetimi.service.base.BaseService;

public interface AnketService extends BaseService<AnketDto, Long> {

    AnketDto olustur(AnketOlusturDto istek);

    void oyKullan(OyKullanDto oyIstek);

    AnketSonucDto sonucGetir(Long anketId);

}
