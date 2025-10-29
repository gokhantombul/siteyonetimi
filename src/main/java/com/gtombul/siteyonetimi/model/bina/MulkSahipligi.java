package com.gtombul.siteyonetimi.model.bina;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import com.gtombul.siteyonetimi.model.insan.Kisi;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Entity
@Table(
        name = "mulk_sahipligi",
        indexes = {
                @Index(name = "ix_ms_daire", columnList = "daire_id"),
                @Index(name = "ix_ms_kisi", columnList = "kisi_id")
        }
)
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("DURUM <> 'SILINDI'")
public class MulkSahipligi extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "daire_id", nullable = false)
    @ToString.Exclude
    private Daire daire;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kisi_id", nullable = false)
    @ToString.Exclude
    private Kisi kisi; // malik

    @Column(name = "pay_orani")
    private Integer payOrani; // yüzde olarak (örn: 100), NULL ise tam mülkiyet

    private LocalDate baslangicTarihi;

    private LocalDate bitisTarihi; // NULL => halen malik

}
