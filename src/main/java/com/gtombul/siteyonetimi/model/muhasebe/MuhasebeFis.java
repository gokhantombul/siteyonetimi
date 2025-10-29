package com.gtombul.siteyonetimi.model.muhasebe;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import com.gtombul.siteyonetimi.model.enums.HareketTuru;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Entity
@Table(name = "muhasebe_fis",
        indexes = {
                @Index(name = "idx_fis_no",   columnList = "fis_no"),
                @Index(name = "idx_fis_durum",columnList = "durum")
        })
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@SQLRestriction("DURUM <> 'SILINDI'")
public class MuhasebeFis extends BaseEntity {

    @Column(name = "fis_no", length = 64, nullable = false)
    private String fisNo;

    @Column(name = "tarih", nullable = false)
    private LocalDate tarih;

    @Enumerated(EnumType.STRING)
    @Column(name = "hareket_turu", nullable = false, length = 32)
    private HareketTuru hareketTuru;

    @Column(name = "aciklama", length = 1024)
    private String aciklama;

}
