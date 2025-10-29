package com.gtombul.siteyonetimi.model.muhasebe;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "aidat_atama_kaydi",
        indexes = {@Index(name = "idx_aak_durum", columnList = "durum")})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@SQLRestriction("DURUM <> 'SILINDI'")
public class AidatAtamaKaydi extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plan_id", nullable = false)
    private AidatPlani plan;

    /**
     * YearMonth "YYYY-MM" olarak saklanıyor
     */
    @Column(name = "donem_yyyy_mm", nullable = false, length = 7)
    private String donemYyyyMm;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "daire_hesap_id", nullable = false)
    private Hesap daireHesap;

}

