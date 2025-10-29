package com.gtombul.siteyonetimi.model.muhasebe;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Muhasebe fişinin tek satırı (kalem).
 * Soft delete BaseEntity.durum ile yönetilir.
 * XOR kuralı ve pozitiflik DB seviyesinde @Check ile garanti edilir.
 */
@Entity
@Table(
        name = "muhasebe_kalem",
        indexes = {
                @Index(name = "idx_kalem_fis", columnList = "fis_id"),
                @Index(name = "idx_kalem_hesap", columnList = "hesap_id"),
                @Index(name = "idx_kalem_durum", columnList = "durum"),
                @Index(name = "idx_kalem_mutabakat_ref", columnList = "mutabakat_ref"),
                @Index(name = "idx_kalem_doviz", columnList = "doviz_cinsi")
        }
)
@Check(constraints = """
         ( (borc_tutar IS NOT NULL AND alacak_tutar IS NULL)
           OR (borc_tutar IS NULL AND alacak_tutar IS NOT NULL) )
         AND COALESCE(borc_tutar,0) >= 0 AND COALESCE(alacak_tutar,0) >= 0
        """)
@Getter
@Setter
@ToString(exclude = {"fis", "hesap"})
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@SQLRestriction("DURUM <> 'SILINDI'")
public class MuhasebeKalem extends BaseEntity {

    /** Başlık fiş */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fis_id", nullable = false)
    private MuhasebeFis fis;

    /** İlgili muhasebe hesabı */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hesap_id", nullable = false)
    private Hesap hesap;

    @Column(name = "borc_tutar", precision = 19, scale = 2)
    private BigDecimal borcTutar;

    @Column(name = "alacak_tutar", precision = 19, scale = 2)
    private BigDecimal alacakTutar;

    @Column(name = "aciklama", length = 512)
    private String aciklama;

    /* -------------------- Döviz opsiyonelleri -------------------- */

    /** Hesabın/dönüşümün döviz cinsi (örn. USD/EUR) */
    @Column(name = "doviz_cinsi", length = 8)
    private String dovizCinsi;

    /** Döviz tutarı (hesap para birimi cinsinden) */
    @Column(name = "doviz_tutar", precision = 19, scale = 6)
    private BigDecimal dovizTutar;

    /** Kullanılan kur (1 döviz = kur TRY) */
    @Column(name = "kur", precision = 19, scale = 6)
    private BigDecimal kur;

    /* -------------------- Banka mutabakatı alanları -------------------- */

    /** Dış referans (banka işlem referansı vb.) */
    @Column(name = "mutabakat_ref", length = 128)
    private String mutabakatRef;

    /** Mutabakat tarihi */
    @Column(name = "mutabakat_tarih")
    private LocalDate mutabakatTarih;

}

