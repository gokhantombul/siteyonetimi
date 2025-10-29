package com.gtombul.siteyonetimi.model.bina;

import com.gtombul.siteyonetimi.model.adres.Ilce;
import com.gtombul.siteyonetimi.model.base.BaseEntity;
import com.gtombul.siteyonetimi.model.enums.FirmaTuru;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "firma")
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Firma extends BaseEntity {

    @Column(name = "firma_unvani", nullable = false, length = 2048)
    private String firmaUnvani;

    @Column(name = "kisa_ad", nullable = false, length = 1024)
    private String kisaAd;

    @Column(name = "adres", nullable = false, length = 2048)
    private String adres;

    @Column(name = "telefon", length = 50)
    private String telefon;

    @Column(name = "internet_adresi")
    private String internetAdresi;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FirmaTuru tur;

    @Column(name = "faks", length = 50)
    private String faks;

    @Column(name = "email", length = 1024)
    private String email;

    @Column(name = "vkn", length = 1024)
    private String vkn;

    @Column(name = "vergi_dairesi", length = 1024)
    private String vergiDairesi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ilce_id", nullable = false)
    @ToString.Exclude
    private Ilce ilce;

    @Column(name = "aciklama", length = 2048)
    private String aciklama;

}
