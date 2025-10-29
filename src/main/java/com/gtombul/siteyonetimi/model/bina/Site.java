package com.gtombul.siteyonetimi.model.bina;

import com.gtombul.siteyonetimi.model.adres.AdresBilgisi;
import com.gtombul.siteyonetimi.model.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "site")
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("DURUM <> 'SILINDI'")
public class Site extends BaseEntity {

    @Column(name = "ad", nullable = false, length = 512)
    private String ad;

    @Column(name = "kisa_ad", length = 100)
    private String kisaAd;

    @Embedded
    private AdresBilgisi adres;

    @Column(name = "telefon", length = 15)
    private String telefon;

    @Column(name = "email")
    private String email;

    @Column(name = "internet_adres")
    private String internetAdres;

    @Column(name = "aciklama", length = 1024)
    private String aciklama;

    @Column(name = "toplam_konut_sayisi", columnDefinition = "int default 0")
    private int toplamKonutSayisi;

    @Column(name = "toplam_dukkan_sayisi", columnDefinition = "int default 0")
    private int toplamDukkanSayisi;


}
