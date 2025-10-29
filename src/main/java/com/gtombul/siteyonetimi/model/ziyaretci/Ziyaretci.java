package com.gtombul.siteyonetimi.model.ziyaretci;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import com.gtombul.siteyonetimi.model.bina.Daire;
import com.gtombul.siteyonetimi.model.enums.YakinlikDerecesi;
import com.gtombul.siteyonetimi.model.enums.ZiyaretciDurum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "ZIYARETCI", indexes = {
        @Index(name = "ix_ziyaretci_ad", columnList = "ad"),
        @Index(name = "ix_ziyaretci_soyad", columnList = "soyad"),
        @Index(name = "ix_ziyaretci_durum", columnList = "ziyaretci_durum"),
        @Index(name = "ix_ziyaretci_daire", columnList = "daire_id")
})
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("DURUM <> 'SILINDI'")
public class Ziyaretci extends BaseEntity {

    @Column(nullable = false, length = 128)
    @Comment("Ziyaretçi adı")
    private String ad;

    @Column(nullable = false, length = 128)
    @Comment("Ziyaretçi soyadı")
    private String soyad;

    @Transient
    private String adSoyad;

    public String getAdSoyad() {
        return (ad == null ? "" : ad) + " " + (soyad == null ? "" : soyad);
    }

    @Column(name = "telefon", length = 20, nullable = false)
    private String telefon;

    @Column(name = "aciklama", length = 512)
    private String aciklama;

    @Enumerated(EnumType.STRING)
    @Column(name = "yakinlik_derecesi", length = 20, nullable = false)
    private YakinlikDerecesi yakinlikDerecesi;

    @Enumerated(EnumType.STRING)
    @Column(name = "ziyaretci_durum", length = 20, nullable = false)
    private ZiyaretciDurum ziyaretciDurum;

    // Gelmemiş ise null olabilir.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daire_id")
    @ToString.Exclude
    private Daire daire;

    @PrePersist
    void prePersist() {
        if (this.yakinlikDerecesi == null) this.yakinlikDerecesi = YakinlikDerecesi.ARKADAS;
        if (this.ziyaretciDurum == null)   this.ziyaretciDurum   = ZiyaretciDurum.DAVETLI;
    }

}

