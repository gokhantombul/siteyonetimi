package com.gtombul.siteyonetimi.service.bina;

import com.gtombul.siteyonetimi.dto.bina.MulkSahipligiDto;
import com.gtombul.siteyonetimi.service.base.BaseService;

import java.time.LocalDate;
import java.util.List;

public interface MulkSahipligiService extends BaseService<MulkSahipligiDto, Long> {

    List<MulkSahipligiDto> aktifSahipler(Long daireId, LocalDate tarih);

}
