package com.gtombul.siteyonetimi.service.bildirim;

import com.gtombul.siteyonetimi.dto.bildirim.BildirimGonderRequestDto;
import com.gtombul.siteyonetimi.dto.bildirim.BildirimResponseDto;

public interface BildirimYoneticisiService {

    BildirimResponseDto gonder(BildirimGonderRequestDto istek);

}
