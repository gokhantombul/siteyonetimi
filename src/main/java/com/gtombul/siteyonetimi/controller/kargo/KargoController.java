package com.gtombul.siteyonetimi.controller.kargo;

import com.gtombul.siteyonetimi.dto.base.PageResponse;
import com.gtombul.siteyonetimi.dto.kargo.KargoDto;
import com.gtombul.siteyonetimi.dto.kargo.KargoListeDto;
import com.gtombul.siteyonetimi.dto.kargo.KargoOlusturGuncelleDto;
import com.gtombul.siteyonetimi.dto.kargo.KargoTeslimDto;
import com.gtombul.siteyonetimi.search.SearchRequest;
import com.gtombul.siteyonetimi.service.kargo.KargoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/kargo")
public class KargoController {

    private final KargoService kargoService;

    @PostMapping
    public ResponseEntity<KargoDto> olustur(@Valid @RequestBody KargoOlusturGuncelleDto dto) {
        return ResponseEntity.ok(kargoService.olustur(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<KargoDto> guncelle(@PathVariable Long id, @Valid @RequestBody KargoOlusturGuncelleDto dto) {
        return ResponseEntity.ok(kargoService.guncelle(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> silSoft(@PathVariable Long id) {
        kargoService.silSoft(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<KargoDto> getir(@PathVariable Long id) {
        return ResponseEntity.ok(kargoService.getir(id));
    }

    @PostMapping("/ara")
    public ResponseEntity<PageResponse<KargoListeDto>> ara(@RequestBody SearchRequest request) {
        return ResponseEntity.ok(kargoService.ara(request));
    }

    @PostMapping("/teslim-et")
    public ResponseEntity<KargoDto> teslimEt(@Valid @RequestBody KargoTeslimDto dto) {
        return ResponseEntity.ok(kargoService.teslimEt(dto));
    }

    @PostMapping("/{id}/iade-et")
    public ResponseEntity<KargoDto> iadeEt(@PathVariable Long id, @RequestParam(required = false) String notlar) {
        return ResponseEntity.ok(kargoService.iadeEt(id, notlar));
    }

    @PostMapping("/{id}/kayip")
    public ResponseEntity<KargoDto> kayipIsaretle(@PathVariable Long id, @RequestParam(required = false) String notlar) {
        return ResponseEntity.ok(kargoService.kayipIsaretle(id, notlar));
    }

}
