package com.gtombul.siteyonetimi.controller.ilan;

import com.gtombul.siteyonetimi.dto.base.PageResponse;
import com.gtombul.siteyonetimi.dto.ilan.IlanDto;
import com.gtombul.siteyonetimi.dto.ilan.IlanGuncelleDto;
import com.gtombul.siteyonetimi.dto.ilan.IlanKaydetDto;
import com.gtombul.siteyonetimi.dto.ilan.IlanListelemeDto;
import com.gtombul.siteyonetimi.search.SearchRequest;
import com.gtombul.siteyonetimi.service.ilan.IlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ilanlar")
public class IlanController {

    private final IlanService ilanService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<IlanDto> olustur(@Valid @RequestBody IlanKaydetDto dto) {
        return ResponseEntity.ok(ilanService.olustur(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<IlanDto> guncelle(@PathVariable Long id, @Valid @RequestBody IlanGuncelleDto dto) {
        dto.setId(id);
        return ResponseEntity.ok(ilanService.guncelle(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IlanDto> getir(@PathVariable Long id) {
        return ResponseEntity.ok(ilanService.getir(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> sil(@PathVariable Long id) {
        ilanService.sil(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Halka açık arama: sadece ONAYLI & AKTIF döner
     */
    @PostMapping("/ara")
    public ResponseEntity<PageResponse<IlanListelemeDto>> ara(@RequestBody SearchRequest request) {
        return ResponseEntity.ok(ilanService.ara(request));
    }

    /**
     * Onay akışı
     */
    @PostMapping("/{id}/yayin-talep")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<IlanDto> yayinTalepEt(@PathVariable Long id) {
        return ResponseEntity.ok(ilanService.yayinTalepEt(id));
    }

    @PostMapping("/admin/{id}/onayla")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<IlanDto> onayla(@PathVariable Long id,
                                          @RequestParam(required = false) String aciklama) {
        return ResponseEntity.ok(ilanService.onayla(id, aciklama));
    }

    @PostMapping("/admin/{id}/reddet")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<IlanDto> reddet(@PathVariable Long id,
                                          @RequestParam String neden) {
        return ResponseEntity.ok(ilanService.reddet(id, neden));
    }

    @PostMapping("/admin/inceleme/ara")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageResponse<IlanListelemeDto>> adminIncelemeAra(@RequestBody SearchRequest req) {
        return ResponseEntity.ok(ilanService.adminIncelemeListesi(req));
    }

}
