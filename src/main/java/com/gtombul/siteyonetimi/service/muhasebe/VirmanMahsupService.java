package com.gtombul.siteyonetimi.service.muhasebe;

import com.gtombul.siteyonetimi.dto.muhasebe.MahsupRequestDto;
import com.gtombul.siteyonetimi.dto.muhasebe.MuhasebeFisDto;
import com.gtombul.siteyonetimi.dto.muhasebe.VirmanRequestDto;

public interface VirmanMahsupService {

    MuhasebeFisDto virmanYap(VirmanRequestDto istek);

    MuhasebeFisDto mahsupOlustur(MahsupRequestDto istek);

}
