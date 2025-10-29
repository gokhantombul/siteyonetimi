package com.gtombul.siteyonetimi.service.duyuru;

import com.gtombul.siteyonetimi.dto.duyuru.DuyuruCreateRequest;
import com.gtombul.siteyonetimi.dto.duyuru.DuyuruResponse;
import com.gtombul.siteyonetimi.dto.duyuru.DuyuruUpdateRequest;
import com.gtombul.siteyonetimi.service.base.BaseService;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * Servisimiz artık GİRDİ DTO'su değil, ÇIKTI DTO'su olan DuyuruResponse'u baz alıyor.
 * Bu sayede listeleme, arama ve get metotları BaseService'den miras alınarak
 * doğrudan DuyuruResponse döndürebilir.
 */
public interface DuyuruService extends BaseService<DuyuruResponse, Long> {

    /**
     * Yeni bir duyuru ve ilişkili dosyalarını oluşturan özel metot.
     *
     * @param request JSON olarak gelen duyuru verileri
     * @param dosyalar Multipart olarak gelen dosyalar
     * @return Oluşturulan duyurunun Response DTO'su
     */
    DuyuruResponse olustur(DuyuruCreateRequest request, MultipartFile[] dosyalar);

    /**
     * Mevcut bir duyuruyu ve ilişkili dosyalarını güncelleyen özel metot.
     *
     * @param uuid    Duyurunun UUID'si
     * @param request JSON olarak gelen güncelleme verileri
     * @param yeniDosyalar Multipart olarak gelen YENİ eklenecek dosyalar
     * @return Güncellenen duyurunun Response DTO'su
     */
    DuyuruResponse guncelle(UUID uuid, DuyuruUpdateRequest request, MultipartFile[] yeniDosyalar);
}