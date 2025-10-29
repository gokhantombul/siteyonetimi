package com.gtombul.siteyonetimi.controller.ziyaretci;

import com.gtombul.siteyonetimi.controller.base.BaseController;
import com.gtombul.siteyonetimi.dto.base.PageResponse;
import com.gtombul.siteyonetimi.dto.ziyaretci.ZiyaretciDto;
import com.gtombul.siteyonetimi.model.enums.ZiyaretciDurum;
import com.gtombul.siteyonetimi.service.ziyaretci.ZiyaretciService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/ziyaretci")
public class ZiyaretciController extends BaseController<ZiyaretciDto, Long> {

    private final ZiyaretciService ziyaretciService;

    protected ZiyaretciController(ZiyaretciService service) {
        super(service);
        this.ziyaretciService = service;
    }

    @GetMapping("/daire/{daireId}")
    public ResponseEntity<PageResponse<ZiyaretciDto>> daireyeGoreListe(
            @PathVariable Long daireId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort
    ) {
        return ResponseEntity.ok(ziyaretciService.daireyeGore(daireId, page, size, sort));
    }

    @GetMapping("/durum")
    public ResponseEntity<PageResponse<ZiyaretciDto>> durumaGoreListe(
            @RequestParam ZiyaretciDurum durum,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort
    ) {
        return ResponseEntity.ok(ziyaretciService.durumaGore(durum, page, size, sort));
    }

    // İşlem uçları (saat tutmuyoruz)
    @PostMapping("/{id}/giris")
    public ResponseEntity<ZiyaretciDto> isaretleGeldi(
            @PathVariable Long id,
            @RequestParam @NotNull Long daireId,
            @RequestParam(required = false) String aciklama
    ) {
        return ResponseEntity.ok(ziyaretciService.isaretleGeldi(id, daireId, aciklama));
    }

    @PostMapping("/{id}/cikis")
    public ResponseEntity<ZiyaretciDto> isaretleCikti(
            @PathVariable Long id,
            @RequestParam(required = false) String aciklama
    ) {
        return ResponseEntity.ok(ziyaretciService.isaretleCikti(id, aciklama));
    }

}
