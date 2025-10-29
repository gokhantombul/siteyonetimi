package com.gtombul.siteyonetimi.service.talep;

import com.gtombul.siteyonetimi.dto.talep.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface TalepService {

    /**
     * Yeni bir talep oluşturur. (Kullanıcı (sakin/kiracı) tarafından kullanılır)
     */
    TalepDetayDto talepOlustur(TalepOlusturRequestDto requestDto);

    /**
     * Bir talebin detaylarını getirir.
     */
    TalepDetayDto getTalepDetay(UUID talepUuid);

    /**
     * Bir talebin durumunu günceller ve TalepGuncelleme kaydı atar. (Yönetici tarafından kullanılır)
     */
    TalepDetayDto talepDurumGuncelle(UUID talepUuid, TalepDurumGuncelleRequestDto requestDto);

    /**
     * Bir talebe istinaden görev oluşturur ve personele atar. (Yönetici tarafından kullanılır)
     */
    TalepDetayDto gorevAta(UUID talepUuid, GorevAtaRequestDto requestDto);

    /**
     * Bir talebe yorum ekler. (Kullanıcı veya Yönetici tarafından kullanılır)
     */
    TalepDetayDto talepYorumEkle(UUID talepUuid, TalepYorumEkleRequestDto requestDto);

    /**
     * Aktif kullanıcının (JWT Token'dan) tüm taleplerini listeler.
     */
    List<TalepListeDto> getKullanicininTalepleri();

    /**
     * Belirli bir personele atanmış tüm görevleri (ve bağlı talepleri) listeler.
     */
    List<TalepListeDto> getPersonelinGorevleri(UUID personelUuid);

    /**
     * Tüm talepleri sayfalama ve filtreleme ile getirir (Yönetici için)
     */
    Page<TalepListeDto> listTumTalepler(Pageable pageable);

    /**
     * Bir talebi (soft) siler. (Durumunu SILINDI yapar)
     */
    void deleteTalep(UUID talepUuid);
}