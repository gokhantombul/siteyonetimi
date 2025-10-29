package com.gtombul.siteyonetimi.controller.muhasebe;

import com.gtombul.siteyonetimi.dto.muhasebe.*;
import com.gtombul.siteyonetimi.service.muhasebe.BankaMutabakatService;
import com.gtombul.siteyonetimi.service.muhasebe.KurFarkiService;
import com.gtombul.siteyonetimi.service.muhasebe.MuhasebeService;
import com.gtombul.siteyonetimi.service.muhasebe.VirmanMahsupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/muhasebe")
@RequiredArgsConstructor
public class MuhasebeController {

    private final MuhasebeService muhasebeService;
    private final VirmanMahsupService virmanMahsupService;
    private final KurFarkiService kurFarkiService;
    private final BankaMutabakatService bankaMutabakatService;

    @PostMapping("/fis")
    //@PreAuthorize("hasRole('SITE_YONETICISI')")
    public ResponseEntity<MuhasebeFisDto> fisOlustur(@RequestBody FisOlusturIstekDto istek) {
        return ResponseEntity.ok(muhasebeService.fisOlustur(istek));
    }

    @PostMapping("/tahsilat")
    //@PreAuthorize("hasRole('SITE_YONETICISI')")
    public ResponseEntity<MuhasebeFisDto> tahsilat(
            @RequestParam Long daireHesapId,
            @RequestParam Long kasaVeyaBankaHesapId,
            @RequestParam BigDecimal tutar,
            @RequestParam(required = false) String aciklama) {
        return ResponseEntity.ok(muhasebeService.tahsilatGir(daireHesapId, kasaVeyaBankaHesapId, tutar, aciklama));
    }

    @PostMapping("/virman")
    //@PreAuthorize("hasRole('SITE_YONETICISI')")
    public ResponseEntity<MuhasebeFisDto> virman(@RequestBody @Valid VirmanRequestDto istek) {
        return ResponseEntity.ok(virmanMahsupService.virmanYap(istek));
    }

    @PostMapping("/mahsup")
    //@PreAuthorize("hasRole('SITE_YONETICISI')")
    public ResponseEntity<MuhasebeFisDto> mahsup(@RequestBody @Valid MahsupRequestDto istek) {
        return ResponseEntity.ok(virmanMahsupService.mahsupOlustur(istek));
    }

    @PostMapping("/kur-farki")
    //@PreAuthorize("hasRole('SITE_YONETICISI')")
    public ResponseEntity<Void> kurFarki(@RequestBody @Valid KurFarkiRequestDto istek) {
        kurFarkiService.kurFarkiOlustur(istek);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/banka/mutabakat")
    //@PreAuthorize("hasRole('SITE_YONETICISI')")
    public ResponseEntity<MutabakatSonucuDto> bankaMutabakat(@RequestBody @Valid MutabakatRequestDto istek) {
        return ResponseEntity.ok(bankaMutabakatService.mutabakatYap(istek));
    }

}