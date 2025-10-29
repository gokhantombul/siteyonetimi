package com.gtombul.siteyonetimi.model.ilan;

import com.gtombul.siteyonetimi.annotation.Searchable;
import com.gtombul.siteyonetimi.model.base.BaseEntity;
import com.gtombul.siteyonetimi.model.insan.Kisi;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ILAN",
        indexes = {
                @Index(name = "ix_ilan_baslik", columnList = "baslik"),
                @Index(name = "ix_ilan_turu", columnList = "turu"),
                @Index(name = "ix_ilan_fiyat", columnList = "fiyat"),
                @Index(name = "ix_ilan_onay", columnList = "onay_durumu"),
                @Index(name = "ix_ilan_durum", columnList = "ilan_durum")
        })
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"resimler", "olusturanKisi", "kategori", "onaylayanKisi"})
@SQLRestriction("DURUM <> 'SILINDI'")
public class Ilan extends BaseEntity {

    @Searchable
    @Column(name = "baslik", length = 255, nullable = false)
    private String baslik;

    @Searchable
    @Column(name = "aciklama", length = 4000)
    private String aciklama;

    @Enumerated(EnumType.STRING)
    @Column(name = "turu", length = 20, nullable = false)
    @Comment("ESYA/HIZMET/ARAC/DIGER")
    private IlanTuru turu;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kategori_id", nullable = false)
    private IlanKategori kategori;

    @Searchable
    @Column(name = "fiyat", precision = 14, scale = 2)
    private BigDecimal fiyat;

    @Enumerated(EnumType.STRING)
    @Column(name = "ilan_durum", length = 20, nullable = false)
    @Builder.Default
    private IlanDurum ilanDurum = IlanDurum.AKTIF;

    @Column(name = "yayim_tarihi")
    private LocalDate yayimTarihi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "olusturan_kisi_id")
    private Kisi olusturanKisi;

    @Enumerated(EnumType.STRING)
    @Column(name = "onay_durumu", length = 20, nullable = false)
    @Builder.Default
    private IlanOnayDurumu onayDurumu = IlanOnayDurumu.TASLAK;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "onaylayan_kisi_id")
    private Kisi onaylayanKisi;

    @Column(name = "onay_tarihi")
    private LocalDateTime onayTarihi;

    @Column(name = "reddetme_nedeni", length = 1024)
    private String reddetmeNedeni;

    @OneToMany(mappedBy = "ilan", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("siraNo ASC")
    @Builder.Default
    private List<IlanResim> resimler = new ArrayList<>();

}

