package com.gtombul.siteyonetimi.storage.ilan;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * Eğer projede genel "DosyaService" varsa buradan bağla.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DosyaServiceAdapter implements DosyaPort {

    // private final DosyaService dosyaService; // <- SENİN servis tipin
    // private final DosyaRepository dosyaRepository; // gerekiyorsa

    @Override
    public Long yukleVeIdDondur(MultipartFile dosya, String klasor) {
        // return dosyaService.yukle(dosya, klasor).getId();
        throw new UnsupportedOperationException("DosyaServiceAdapter: imzaya göre doldurun");
    }

    @Override
    public String publicUrl(Long kaynakKayitId) {
        // var d = dosyaService.getir(kaynakKayitId); return d.getUrl();
        throw new UnsupportedOperationException("DosyaServiceAdapter: imzaya göre doldurun");
    }

    @Override
    public void fizikselSil(Long kaynakKayitId) {
        // dosyaService.sil(kaynakKayitId);
    }

}
