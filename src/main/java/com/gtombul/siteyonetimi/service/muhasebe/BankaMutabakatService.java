package com.gtombul.siteyonetimi.service.muhasebe;

import com.gtombul.siteyonetimi.dto.muhasebe.MutabakatRequestDto;
import com.gtombul.siteyonetimi.dto.muhasebe.MutabakatSonucuDto;

public interface BankaMutabakatService {

    MutabakatSonucuDto mutabakatYap(MutabakatRequestDto istek);

}
