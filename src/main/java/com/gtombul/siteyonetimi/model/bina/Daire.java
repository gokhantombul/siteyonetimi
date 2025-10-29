package com.gtombul.siteyonetimi.model.bina;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import com.gtombul.siteyonetimi.model.enums.DaireTipi;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(
        name = "daire",
        indexes = {
                @Index(name = "ix_daire_kat_id", columnList = "kat_id"),
                @Index(name = "ix_daire_no", columnList = "no")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_daire_blok_no", columnNames = {"blok_id", "no"})
        }
)
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("DURUM <> 'SILINDI'")
public class Daire extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kat_id", nullable = false)
    @ToString.Exclude
    private Kat kat;

    // Sık erişim için blok'a da doğrudan FK (read-cost düşürme amaçlı denormalizasyon)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "blok_id", nullable = false)
    @ToString.Exclude
    private Blok blok;

    @Column(nullable = false, length = 30)
    private String no; // Örn: 10, 10A

    @Column(name = "bagimsiz_bolum_no", length = 50)
    private String bagimsizBolumNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tip", nullable = false, length = 20)
    private DaireTipi tip;

    @Column(name = "metrekare")
    private Integer metrekare;

    @Column(name = "oda_sayisi")
    private Integer odaSayisi;

}
