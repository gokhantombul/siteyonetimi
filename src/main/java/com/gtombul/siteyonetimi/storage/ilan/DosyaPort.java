package com.gtombul.siteyonetimi.storage.ilan;

import org.springframework.web.multipart.MultipartFile;

/**
 * Domain yalnızca bu portu görür. Gerçek implementasyon senin upload servisin.
 */
public interface DosyaPort {

    Long yukleVeIdDondur(MultipartFile dosya, String klasor); // ör: "ilan/{ilanId}"

    String publicUrl(Long kaynakKayitId);

    void fizikselSil(Long kaynakKayitId);

}