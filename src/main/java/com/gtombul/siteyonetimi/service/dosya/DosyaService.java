package com.gtombul.siteyonetimi.service.dosya;

import com.gtombul.siteyonetimi.model.dosya.Dosya;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface DosyaService {

    /**
     * Bir dosyayı sisteme kaydeder.
     *
     * @param dosya    Yüklenecek dosya
     * @param modulAdi Dosyanın yüklendiği modül (örn: "duyurular", "aidat-dekontlari")
     * @return Kaydedilen dosyanın veritabanı nesnesi
     */
    Dosya kaydet(MultipartFile dosya, String modulAdi);

    Resource dosyaGetir(UUID uuid);

    Dosya metaVeriGetir(UUID uuid);

    void sil(UUID uuid);

    /**
     * Verilen Dosya entity'si için public URL oluşturur.
     * Bu, URL oluşturma mantığını merkezi hale getirir.
     */
    String publicUrlAl(Dosya dosya);

}