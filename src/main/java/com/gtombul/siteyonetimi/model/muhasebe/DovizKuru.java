package com.gtombul.siteyonetimi.model.muhasebe;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import com.gtombul.siteyonetimi.model.enums.ParaBirimi;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "doviz_kuru",
        indexes = {@Index(name = "idx_kur_tarih_cift", columnList = "tarih, kaynak, hedef"),
                @Index(name = "idx_kur_durum", columnList = "durum")}
)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@SQLRestriction("DURUM <> 'SILINDI'")
public class DovizKuru extends BaseEntity {

    @Column(name = "tarih", nullable = false)
    private LocalDate tarih;

    @Enumerated(EnumType.STRING)
    @Column(name = "kaynak", length = 8, nullable = false)
    private ParaBirimi kaynak; // USD, EUR...

    @Enumerated(EnumType.STRING)
    @Column(name = "hedef", length = 8, nullable = false)
    private ParaBirimi hedef; // TRY vb.

    @Column(name = "oran", precision = 19, scale = 6, nullable = false)
    private BigDecimal oran; // 1 kaynak = oran hedef

}
