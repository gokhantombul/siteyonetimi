package com.gtombul.siteyonetimi.model.demirbas;

import com.gtombul.siteyonetimi.model.base.BaseEntity;
import com.gtombul.siteyonetimi.model.enums.StokIslemTuru;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

@Entity
@Table(name = "STOK_HAREKET")
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("DURUM <> 'SILINDI'")
public class StokHareket extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "demirbas_id", nullable = false)
    @ToString.Exclude
    private Demirbas demirbas;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "depo_id", nullable = false)
    @ToString.Exclude
    private Depo depo;

    @Enumerated(EnumType.STRING)
    @Column(name = "islem_turu", length = 16, nullable = false)
    private StokIslemTuru islemTuru;

    @Column(name = "miktar", precision = 19, scale = 3, nullable = false)
    private BigDecimal miktar;

    @Column(name = "referans_no", length = 128)
    private String referansNo; // irsaliye/fatura/iş emri no vs.

    @Column(name = "aciklama", length = 1024)
    private String aciklama;

}
