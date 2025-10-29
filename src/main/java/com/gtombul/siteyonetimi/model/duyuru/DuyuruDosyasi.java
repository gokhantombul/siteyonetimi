package com.gtombul.siteyonetimi.model.duyuru;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import com.gtombul.siteyonetimi.model.dosya.Dosya; // <-- YENİ İLİŞKİ
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "duyuru_dosyalari")
public class DuyuruDosyasi extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "duyuru_id", nullable = false)
    private Duyuru duyuru;

    /**
     * ESKİSİ:
     * @Column(name = "dosya_kayit_id", nullable = false)
     * private Long dosyaKayitId;
     * * YENİSİ:
     * Artık doğrudan merkezi Dosya entity'sine bağlanıyoruz.
     * Bu, mapping'i ve veri bütünlüğünü çok daha kolay hale getirir.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dosya_id", nullable = false)
    private Dosya dosya;

    /**
     * Orijinal dosya adını artık merkezi Dosya entity'si tuttuğu için
     * bu alana burada (duplike olarak) ihtiyacımız kalmadı.
     * ESKİSİ:
     * @Column(name = "dosya_adi", nullable = false)
     * private String dosyaAdi;
     */
}