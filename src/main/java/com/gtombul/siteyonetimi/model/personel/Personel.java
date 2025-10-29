package com.gtombul.siteyonetimi.model.personel;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import com.gtombul.siteyonetimi.model.enums.Durum;
import com.gtombul.siteyonetimi.model.enums.PersonelGorev;
import com.gtombul.siteyonetimi.model.insan.Kisi;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "PERSONEL",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_personel_kisi", columnNames = {"kisi_id"})
        })
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Personel extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kisi_id", nullable = false, foreignKey = @ForeignKey(name = "fk_personel_kisi"))
    @ToString.Exclude
    private Kisi kisi;

    @Enumerated(EnumType.STRING)
    @Column(name = "gorev", length = 32, nullable = false)
    private PersonelGorev gorev;

    @Column(name = "ise_giris_tarihi", nullable = false)
    private LocalDate iseGirisTarihi;

    @Column(name = "ise_cikis_tarihi")
    private LocalDate iseCikisTarihi;

    @Column(name = "maas", precision = 18, scale = 2)
    private BigDecimal maas;

    @Column(name = "aciklama", length = 1024)
    private String aciklama;

    // Performans için türetilmiş, DB’de tutulmayan alan:
    @Transient
    public boolean isCalisiyor() {
        return getIseCikisTarihi() == null && getDurum().equals(Durum.AKTIF); // BaseEntity.durum’a göre aktif kontrolü
    }

}