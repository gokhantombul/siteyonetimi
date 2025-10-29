package com.gtombul.siteyonetimi.controller.talep;

import com.gtombul.siteyonetimi.dto.talep.*;
import com.gtombul.siteyonetimi.service.talep.TalepService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/talepler")
@RequiredArgsConstructor
public class TalepController {

    private final TalepService talepService;

    // --- Kullanıcı (Sakin/Kiracı) Endpoint'leri ---

    /**
     * (Kullanıcı) Yeni bir talep oluşturur.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    // @PreAuthorize("hasRole('SAKIN') or hasRole('KIRACI')") // Security eklenecek
    public TalepDetayDto talepOlustur(@RequestBody @Valid TalepOlusturRequestDto requestDto) {
        return talepService.talepOlustur(requestDto);
    }

    /**
     * (Kullanıcı) Aktif kullanıcının kendi taleplerini listeler.
     */
    @GetMapping("/taleplerim")
    // @PreAuthorize("hasRole('SAKIN') or hasRole('KIRACI')")
    public ResponseEntity<List<TalepListeDto>> kullanicininTalepleriniGetir() {
        return ResponseEntity.ok(talepService.getKullanicininTalepleri());
    }

    /**
     * (Kullanıcı veya Yönetici) Mevcut bir talebe yorum ekler.
     */
    @PostMapping("/{uuid}/yorum")
    // @PreAuthorize("isAuthenticated()") // Güvenlik: Sadece otantike kullanıcılar
    public TalepDetayDto yorumaEkle(@PathVariable UUID uuid, @RequestBody @Valid TalepYorumEkleRequestDto requestDto) {
        return talepService.talepYorumEkle(uuid, requestDto);
    }

    // --- Yönetici/Personel Endpoint'leri ---

    /**
     * (Yönetici/Personel/Kullanıcı) Bir talebin detayını getirir.
     */
    @GetMapping("/{uuid}")
    // @PreAuthorize("isAuthenticated()") // Güvenlik: Talep sahibi, ilgili personel veya yönetici görebilmeli
    public TalepDetayDto talepDetayGetir(@PathVariable UUID uuid) {
        return talepService.getTalepDetay(uuid);
    }

    /**
     * (Yönetici) Tüm talepleri sayfalı olarak listeler.
     */
    @GetMapping
    // @PreAuthorize("hasRole('YONETICI')")
    public ResponseEntity<Page<TalepListeDto>> tumTalepleriListele(Pageable pageable) {
        return ResponseEntity.ok(talepService.listTumTalepler(pageable));
    }

    /**
     * (Yönetici veya Personel) Belirli bir personele atanmış görevleri (talepleri) listeler.
     */
    @GetMapping("/personel/{personelUuid}")
    // @PreAuthorize("hasRole('YONETICI') or (hasRole('PERSONEL') and #personelUuid == authentication.principal.uuid)")
    public ResponseEntity<List<TalepListeDto>> personelinGorevleriniGetir(@PathVariable UUID personelUuid) {
        return ResponseEntity.ok(talepService.getPersonelinGorevleri(personelUuid));
    }

    /**
     * (Yönetici) Bir talebin durumunu günceller (örn: İşleme Alındı, Tamamlandı).
     */
    @PutMapping("/{uuid}/durum")
    // @PreAuthorize("hasRole('YONETICI')")
    public TalepDetayDto talepDurumGuncelle(@PathVariable UUID uuid, @RequestBody @Valid TalepDurumGuncelleRequestDto requestDto) {
        return talepService.talepDurumGuncelle(uuid, requestDto);
    }

    /**
     * (Yönetici) Bir talebe istinaden görev oluşturur ve personele atar.
     */
    @PostMapping("/{uuid}/gorev-ata")
    @ResponseStatus(HttpStatus.CREATED)
    // @PreAuthorize("hasRole('YONETICI')")
    public TalepDetayDto gorevAta(@PathVariable UUID uuid, @RequestBody @Valid GorevAtaRequestDto requestDto) {
        return talepService.gorevAta(uuid, requestDto);
    }

    /**
     * (Yönetici) Bir talebi (soft) siler.
     */
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    // @PreAuthorize("hasRole('YONETICI')")
    public void talepSil(@PathVariable UUID uuid) {
        talepService.deleteTalep(uuid);
    }
}