package com.gtombul.siteyonetimi.model.bina;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import com.gtombul.siteyonetimi.model.insan.Kisi;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
        name = "kira_sozlesmesi",
        indexes = {
                @Index(name = "ix_kira_daire", columnList = "daire_id"),
                @Index(name = "ix_kira_kiraci", columnList = "kiraci_id")
        }
)
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("DURUM <> 'SILINDI'")
public class KiraSozlesmesi extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "daire_id", nullable = false)
    @ToString.Exclude
    private Daire daire;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kiraci_id", nullable = false)
    @ToString.Exclude
    private Kisi kiraci;

    private LocalDate baslangicTarihi;

    private LocalDate bitisTarihi; // açık uçlu olabilir

    @Column(precision = 14, scale = 2)
    private BigDecimal kiraBedeli;

    @Column(precision = 14, scale = 2)
    private BigDecimal depozito;

    @Column(length = 3)
    private String paraBirimi; // TRY, USD, EUR

}
