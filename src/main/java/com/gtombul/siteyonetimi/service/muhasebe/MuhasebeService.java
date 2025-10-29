package com.gtombul.siteyonetimi.service.muhasebe;

import com.gtombul.siteyonetimi.dto.muhasebe.FisOlusturIstekDto;
import com.gtombul.siteyonetimi.dto.muhasebe.MuhasebeFisDto;

import java.math.BigDecimal;

public interface MuhasebeService {

    MuhasebeFisDto fisOlustur(FisOlusturIstekDto istek);

    MuhasebeFisDto tahsilatGir(Long daireHesapId, Long kasaVeyaBankaHesapId, BigDecimal tutar, String aciklama);

}
