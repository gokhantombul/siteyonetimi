package com.gtombul.siteyonetimi.controller.bildirim;

import com.gtombul.siteyonetimi.dto.bildirim.BildirimGonderRequestDto;
import com.gtombul.siteyonetimi.dto.bildirim.BildirimResponseDto;
import com.gtombul.siteyonetimi.service.bildirim.BildirimYoneticisiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bildirim")
@RequiredArgsConstructor
@Slf4j
public class BildirimController {

    private final BildirimYoneticisiService bildirimServisi;

    @PostMapping("/gonder")
    public ResponseEntity<BildirimResponseDto> gonder(@RequestBody BildirimGonderRequestDto dto) {
        // GlobalHandler ile validasyon hatalarını yakalıyorsun; burada minimal:
        var sonuc = bildirimServisi.gonder(dto);
        return ResponseEntity.ok(sonuc);
    }

}
