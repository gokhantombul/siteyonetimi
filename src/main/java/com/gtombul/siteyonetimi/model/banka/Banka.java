package com.gtombul.siteyonetimi.model.banka;

import com.gtombul.siteyonetimi.model.adres.Ilce;
import com.gtombul.siteyonetimi.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "banka")
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Banka extends BaseEntity {

    @Column(name = "ad", length = 1024, nullable = false)
    private String ad;

    @Column(name = "merkez_adres", length = 2048, nullable = false)
    private String merkezAdres;

    @Column(name = "telefon", length = 20, nullable = false)
    private String telefon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ilce_id", nullable = false)
    @ToString.Exclude
    private Ilce merkezAdresIlce;

    @Column(name = "aciklama", length =  1024)
    private String aciklama;

    @Column(name = "postaKod")
    private Integer postaKod;

}
