package com.gtombul.siteyonetimi.model.muhasebe;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import com.gtombul.siteyonetimi.model.enums.HesaplamaTipi;
import com.gtombul.siteyonetimi.model.enums.ParaBirimi;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

@Entity
@Table(name = "aidat_plani",
        indexes = {@Index(name = "idx_aidat_plani_durum", columnList = "durum")})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@SQLRestriction("DURUM <> 'SILINDI'")
public class AidatPlani extends BaseEntity {

    @Column(name = "ad", nullable = false, length = 255)
    private String ad;

    @Enumerated(EnumType.STRING)
    @Column(name = "hesaplama_tipi", nullable = false, length = 16)
    private HesaplamaTipi hesaplamaTipi;

    /**
     * SABIT için kullanılır; M2/KATSAYI için çarpan olarak kullanılabilir
     */
    @Column(name = "tutar", precision = 19, scale = 2, nullable = false)
    private BigDecimal tutar;

    @Enumerated(EnumType.STRING)
    @Column(name = "para_birimi", nullable = false, length = 8)
    private ParaBirimi paraBirimi;

    /**
     * Vade günü (ör. her ayın 10’u)
     */
    @Column(name = "vade_gunu", nullable = false)
    private Integer vadeGunu;

    /**
     * Gecikme için günlük oran (örn. 0.0005 = %0.05/gün)
     */
    @Column(name = "gecikme_orani_gunluk", precision = 10, scale = 6)
    private BigDecimal gecikmeOraniGunluk;

    /**
     * Null ise tüm daireler; doluysa filtreleme için blok kodu
     */
    @Column(name = "blok_kodu", length = 64)
    private String blokKodu;

}
