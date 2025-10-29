package com.gtombul.siteyonetimi.model.demirbas;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

@Entity
@Table(name = "DEMIRBAS")
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("DURUM <> 'SILINDI'")
public class Demirbas extends BaseEntity {

    @Column(name = "ad", length = 255, nullable = false)
    private String ad;

    @Column(name = "barkod", length = 128)
    private String barkod;

    @Column(name = "etiket_no", length = 128)
    private String etiketNo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kategori_id", nullable = false)
    @ToString.Exclude
    private DemirbasKategori kategori;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "depo_id", nullable = false)
    @ToString.Exclude
    private Depo depo;

    @Column(name = "miktar", precision = 19, scale = 3, nullable = false)
    private BigDecimal miktar; // adet, kg, mt, vs. (UI’da birim seçtirebilirsin)

    @Column(name = "birim", length = 32, nullable = false)
    private String birim; // "ADET", "KG" vb.

    @Column(name = "minimum_seviye", precision = 19, scale = 3)
    private BigDecimal minimumSeviye; // kritik stok uyarısı için

    @Column(name = "aciklama", length = 1024)
    private String aciklama;

}
