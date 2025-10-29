package com.gtombul.siteyonetimi.model.kargo;

import com.gtombul.siteyonetimi.annotation.Searchable;
import com.gtombul.siteyonetimi.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLRestriction;

import java.time.OffsetDateTime;

@Entity
@Table(name = "KARGO", indexes = {
        @Index(name = "ix_kargo_takip_no", columnList = "takip_no", unique = true),
        @Index(name = "ix_kargo_daire_id", columnList = "daire_id"),
        @Index(name = "ix_kargo_durum", columnList = "kargo_durum")
})
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("DURUM <> 'SILINDI'")
public class Kargo extends BaseEntity {

    @Searchable
    @Column(name = "kargo_sirketi", length = 255, nullable = false)
    private String kargoSirketi;

    @Searchable
    @Column(name = "takip_no", length = 128, nullable = false, unique = true)
    private String takipNo;

    // Düşük maliyetli ilişki: doğrudan id tutuyoruz (join yok, liste için ucuz)
    @Column(name = "daire_id", nullable = false)
    private Long daireId;

    @Searchable
    @Column(name = "alici_ad_soyad", length = 255, nullable = false)
    private String aliciAdSoyad;

    @Column(name = "alici_telefon", length = 32)
    private String aliciTelefon;

    @Comment("Kargonun siteye fiziksel olarak giriş yaptığı zaman")
    @Column(name = "giris_zamani")
    private OffsetDateTime girisZamani;

    @Comment("Daire sakinine teslim edildiği zaman")
    @Column(name = "teslim_zamani")
    private OffsetDateTime teslimZamani;

    @Column(name = "teslim_alan", length = 255)
    private String teslimAlan; // Teslim alan kişinin adı (sakin/vekil)

    @Column(name = "teslim_kodu", length = 16)
    private String teslimKodu; // Opsiyonel: OTP ya da kısa teslim PIN'i

    @Enumerated(EnumType.STRING)
    @Column(name = "kargo_durum", length = 32, nullable = false)
    private KargoDurum kargoDurum;

    @Column(name = "notlar", length = 1024)
    private String notlar;

    // Güvenlik görevlisi / teslimi alan personel vs. için iz sürme alanları (join yerine minimal string/id)
    @Column(name = "giris_kaydeden_kullanici", length = 255)
    private String girisKaydedenKullanici;

    @Column(name = "teslim_kaydeden_kullanici", length = 255)
    private String teslimKaydedenKullanici;

}
