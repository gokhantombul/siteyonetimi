package com.gtombul.siteyonetimi.service.ziyaretci;

import com.gtombul.siteyonetimi.dto.base.PageResponse;
import com.gtombul.siteyonetimi.dto.ziyaretci.ZiyaretciDto;
import com.gtombul.siteyonetimi.model.enums.ZiyaretciDurum;
import com.gtombul.siteyonetimi.service.base.BaseService;

public interface ZiyaretciService extends BaseService<ZiyaretciDto, Long> {

    PageResponse<ZiyaretciDto> daireyeGore(Long daireId, Integer page, Integer size, String sort);

    PageResponse<ZiyaretciDto> durumaGore(ZiyaretciDurum durum, Integer page, Integer size, String sort);

    //PageResponse<ZiyaretciDto> ara(SearchRequest request);

    // İşlem metotları (saat tutmuyoruz):
    ZiyaretciDto isaretleGeldi(Long ziyaretciId, Long daireId, String aciklama);

    ZiyaretciDto isaretleCikti(Long ziyaretciId, String aciklama);

}
