package com.gtombul.siteyonetimi.model.bina;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(
        name = "kat",
        indexes = {
                @Index(name = "ix_kat_blok_id", columnList = "blok_id"),
                @Index(name = "ix_kat_sira_no", columnList = "sira_no")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_kat_blok_sira", columnNames = {"blok_id", "sira_no"})
        }
)
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("DURUM <> 'SILINDI'")
public class Kat extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "blok_id", nullable = false)
    @ToString.Exclude
    private Blok blok;

    // Zemin = 0, Bodrum = negatif tercih edebilirsiniz
    @Column(name = "sira_no", nullable = false)
    private Integer siraNo;


    @Column(length = 100)
    private String ad; // Örn: Zemin, 1. Kat

}
