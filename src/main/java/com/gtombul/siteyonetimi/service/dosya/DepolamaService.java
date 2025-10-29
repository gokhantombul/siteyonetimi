package com.gtombul.siteyonetimi.service.dosya;

import com.gtombul.siteyonetimi.model.dosya.DepolamaTipi;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface DepolamaService {

    /**
     * Hangi depolama tipini desteklediğini belirtir.
     */
    DepolamaTipi getDepolamaTipi();

    /**
     * Verilen dosyayı belirtilen yola kaydeder.
     *
     * @return Kaydedilen dosyanın tam yolu veya S3'teki nesne anahtarı.
     */
    String kaydet(MultipartFile dosya, String dosyaYolu, String benzersizAdi);

    /**
     * Belirtilen yoldaki dosyayı yükler.
     */
    Resource yukle(String dosyaYolu, String benzersizAdi);

    /**
     * Belirtilen yoldaki dosyayı siler.
     */
    void sil(String dosyaYolu, String benzersizAdi);

    /**
     * Dosya için public erişim URL'i oluşturur.
     */
    String publicUrlAl(String dosyaYolu, String benzersizAdi);

}