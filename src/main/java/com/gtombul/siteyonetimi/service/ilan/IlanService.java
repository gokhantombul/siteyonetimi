package com.gtombul.siteyonetimi.service.ilan;

import com.gtombul.siteyonetimi.dto.base.PageResponse;
import com.gtombul.siteyonetimi.dto.ilan.IlanDto;
import com.gtombul.siteyonetimi.dto.ilan.IlanGuncelleDto;
import com.gtombul.siteyonetimi.dto.ilan.IlanKaydetDto;
import com.gtombul.siteyonetimi.dto.ilan.IlanListelemeDto;
import com.gtombul.siteyonetimi.search.SearchRequest;

public interface IlanService {

    IlanDto olustur(IlanKaydetDto dto);

    IlanDto guncelle(IlanGuncelleDto dto);

    void sil(Long id);

    IlanDto getir(Long id);

    PageResponse<IlanListelemeDto> ara(SearchRequest request);

    // Onay akışı
    IlanDto yayinTalepEt(Long ilanId);

    IlanDto onayla(Long ilanId, String aciklamaOpsiyonel);

    IlanDto reddet(Long ilanId, String reddetmeNedeni);

    PageResponse<IlanListelemeDto> adminIncelemeListesi(SearchRequest request);

}
