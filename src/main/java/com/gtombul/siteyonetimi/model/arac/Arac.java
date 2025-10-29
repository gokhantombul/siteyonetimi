package com.gtombul.siteyonetimi.model.arac;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import com.gtombul.siteyonetimi.model.insan.Kisi;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(
        name = "arac",
        indexes = {
                @Index(name = "ix_arac_plaka", columnList = "plaka"),
                @Index(name = "ix_arac_kisi_id", columnList = "kisi_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_arac_plaka", columnNames = {"plaka"})
        }
)
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("DURUM <> 'SILINDI'")
public class Arac extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String marka;

    @Column(nullable = false, length = 100)
    private String model;

    @Column(nullable = false, length = 100)
    private String renk;

    @Column(name = "yil", nullable = false, columnDefinition = "int default 0")
    private Integer yil;

    @Column(nullable = false, length = 20)
    private String plaka;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kisi_id", nullable = false)
    @ToString.Exclude
    private Kisi kisi; // Araç sahibini kişi ile eşleştiriyoruz

    @Column(name = "aciklama", length = 1024)
    private String aciklama;

}
