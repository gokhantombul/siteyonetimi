package com.gtombul.siteyonetimi.service.kargo;

import com.gtombul.siteyonetimi.dto.base.PageResponse;
import com.gtombul.siteyonetimi.dto.kargo.KargoDto;
import com.gtombul.siteyonetimi.dto.kargo.KargoListeDto;
import com.gtombul.siteyonetimi.dto.kargo.KargoOlusturGuncelleDto;
import com.gtombul.siteyonetimi.dto.kargo.KargoTeslimDto;
import com.gtombul.siteyonetimi.search.SearchRequest;

public interface KargoService {

    KargoDto olustur(KargoOlusturGuncelleDto dto);
    KargoDto guncelle(Long id, KargoOlusturGuncelleDto dto);
    void silSoft(Long id);

    KargoDto getir(Long id);
    PageResponse<KargoListeDto> ara(SearchRequest request);

    KargoDto teslimEt(KargoTeslimDto dto);
    KargoDto iadeEt(Long id, String notlar);
    KargoDto kayipIsaretle(Long id, String notlar);

}
