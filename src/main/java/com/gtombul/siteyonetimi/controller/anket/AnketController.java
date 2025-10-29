package com.gtombul.siteyonetimi.controller.anket;

import com.gtombul.siteyonetimi.controller.base.BaseController;
import com.gtombul.siteyonetimi.dto.anket.AnketDto;
import com.gtombul.siteyonetimi.dto.anket.AnketOlusturDto;
import com.gtombul.siteyonetimi.dto.anket.AnketSonucDto;
import com.gtombul.siteyonetimi.dto.anket.OyKullanDto;
import com.gtombul.siteyonetimi.service.anket.AnketService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/anket")
public class AnketController extends BaseController<AnketDto, Long> {

    private final AnketService anketService;

    protected AnketController(AnketService anketService) {
        super(anketService);
        this.anketService = anketService;
    }

    @PostMapping("/olustur")
    public ResponseEntity<AnketDto> olustur(@Valid @RequestBody AnketOlusturDto istek) {
        return ResponseEntity.ok(anketService.olustur(istek));
    }

    @PostMapping("/{anketId}/oy")
    public ResponseEntity<Void> oyKullan(@PathVariable Long anketId,
                                         @Valid @RequestBody OyKullanDto oy) {
        oy.setAnketId(anketId);
        anketService.oyKullan(oy);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{anketId}/sonuc")
    public ResponseEntity<AnketSonucDto> sonuc(@PathVariable Long anketId) {
        return ResponseEntity.ok(anketService.sonucGetir(anketId));
    }

}